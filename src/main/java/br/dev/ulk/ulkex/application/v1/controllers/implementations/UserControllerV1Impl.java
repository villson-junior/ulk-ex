package br.dev.ulk.ulkex.application.v1.controllers.implementations;

import br.dev.ulk.ulkex.application.v1.controllers.UserControllerV1;
import br.dev.ulk.ulkex.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.ulkex.core.domain.entities.Role;
import br.dev.ulk.ulkex.core.domain.entities.User;
import br.dev.ulk.ulkex.core.domain.enumerations.RoleEnum;
import br.dev.ulk.ulkex.infraestructure.repositories.RoleRepository;
import br.dev.ulk.ulkex.infraestructure.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserControllerV1Impl implements UserControllerV1 {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserControllerV1Impl(UserRepository userRepository, RoleRepository roleRepository,
        BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Void> createUser(UserRequestDTO dto) {

        Role role = roleRepository.findByName(RoleEnum.ADMIN).get();

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = User.builder()
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Set.of(role))
            .build();

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<User>> readUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}