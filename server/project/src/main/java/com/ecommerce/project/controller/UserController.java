package com.ecommerce.project.controller;

import com.ecommerce.project.exceptions.*;
import com.ecommerce.project.model.User;
import com.ecommerce.project.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            String token = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")

    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        try {
            String token = userService.loginUser(credentials.get("email"), credentials.get("password"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (InvalidPasswordException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
    }
}
