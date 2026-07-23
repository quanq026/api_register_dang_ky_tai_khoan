package com.rikkei.course141.ss1.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rikkei.course141.ss1.dto.request.RegisterRequest;
import com.rikkei.course141.ss1.model.User;
import com.rikkei.course141.ss1.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }
        User user = User.builder()
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .fullName(dto.getFullName())
            .role("USER")
            .enabled(true)
            .build();
        return userRepository.save(user);
    }
}
