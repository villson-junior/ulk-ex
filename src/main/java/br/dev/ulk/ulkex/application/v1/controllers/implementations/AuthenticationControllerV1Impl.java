package br.dev.ulk.ulkex.application.v1.controllers.implementations;

import br.dev.ulk.ulkex.application.v1.controllers.AuthenticationControllerV1;
import br.dev.ulk.ulkex.application.v1.payloads.AuthResponseDTO;
import br.dev.ulk.ulkex.application.v1.payloads.LoginRequestDTO;
import br.dev.ulk.ulkex.core.domain.entities.User;
import br.dev.ulk.ulkex.infraestructure.repositories.UserRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationControllerV1Impl implements AuthenticationControllerV1 {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${application.name}")
    private String applicationName;

    public AuthenticationControllerV1Impl(
        JwtEncoder jwtEncoder,
        UserRepository userRepository,
        BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO loginRequest) {

        Optional<User> optUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optUser.isEmpty() || !optUser.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("invalid username or password");
        }

        long expiresIn = 300L;

        String scopes = optUser.get().getRoles()
            .stream()
            .map(r -> r.getName().getDescription())
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(applicationName)
            .subject(optUser.get().getId().toString())
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new AuthResponseDTO(jwtValue, expiresIn));
    }
}