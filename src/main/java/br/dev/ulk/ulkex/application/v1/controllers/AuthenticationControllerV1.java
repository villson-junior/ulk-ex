package br.dev.ulk.ulkex.application.v1.controllers;

import br.dev.ulk.ulkex.application.v1.payloads.AuthResponseDTO;
import br.dev.ulk.ulkex.application.v1.payloads.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerV1 {

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequest);

}