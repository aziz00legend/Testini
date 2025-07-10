package com.example.backend.securityjwt.controller.dto;

import com.example.backend.securityjwt.user.Role;

public record RegisterRequest(Integer id, String firstname, String lastname, String email, String password, Role role) {
}