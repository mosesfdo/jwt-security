package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String msg = userService.register(username, password);
        if ("User registered successfully!".equals(msg)) {
            return ResponseEntity.ok(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Map<String, String> res = userService.login(username, password);
        if ("Login successful".equals(res.get("message"))) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(401).body(res);
    }
}
