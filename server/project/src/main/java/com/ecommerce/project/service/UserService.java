package com.ecommerce.project.service;

import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.exceptions.*;
import com.ecommerce.project.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        String rawPassword = user.getPassword();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return loginUser(user.getEmail(), rawPassword);

    }

    public String loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty())
            throw new RuntimeException("User not found");

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
