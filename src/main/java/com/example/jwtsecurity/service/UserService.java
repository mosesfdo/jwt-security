package com.example.jwtsecurity.service;

import com.example.jwtsecurity.entity.User;
import com.example.jwtsecurity.repo.UserRepository;
import com.example.jwtsecurity.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public String register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            return "User already exists";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(u);
        return "User registered successfully!";
    }

    public Map<String, String> login(String username, String rawPassword) {
        var u = userRepository.findByUsername(username).orElse(null);
        Map<String, String> res = new HashMap<>();
        if (u != null && passwordEncoder.matches(rawPassword, u.getPassword())) {
            String token = jwtUtil.generateToken(username);
            res.put("message", "Login successful");
            res.put("token", token);
            return res;
        }
        res.put("message", "Invalid username or password");
        return res;
    }
}
