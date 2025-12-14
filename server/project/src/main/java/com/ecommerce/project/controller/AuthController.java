package com.ecommerce.project.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.UserRepository;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = "http://localhost:5173")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
