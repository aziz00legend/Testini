package com.example.backend.securityjwt.controller;


import com.example.backend.securityjwt.controller.dto.AuthenticationRequest;

import com.example.backend.securityjwt.controller.dto.AuthenticationResponse;
import com.example.backend.securityjwt.controller.dto.RegisterRequest;
import com.example.backend.securityjwt.repo.UserRepository;
import com.example.backend.securityjwt.service.AuthenticationService;
import com.example.backend.securityjwt.user.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AuthenticationService authenticationService ,UserRepository userRepository) {


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody RegisterRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("User not found"));

        // Mettre à jour les informations de l'utilisateur
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());


        // Sauvegarder les changements
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Récupère l'email de l'utilisateur authentifié
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
}
