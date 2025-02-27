package br.dev.ulk.ulkex.application.v1.controllers;

import br.dev.ulk.ulkex.application.v1.payloads.UserRequestDTO;
import br.dev.ulk.ulkex.core.domain.entities.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerV1 {

    @Transactional
    @PostMapping("/users")
    ResponseEntity<Void> createUser(@RequestBody UserRequestDTO dto);

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<List<User>> readUsers();

}