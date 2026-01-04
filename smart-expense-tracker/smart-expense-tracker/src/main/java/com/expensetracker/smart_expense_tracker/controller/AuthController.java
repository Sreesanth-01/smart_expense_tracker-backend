package com.expensetracker.smart_expense_tracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.smart_expense_tracker.dto.LoginRequest;
import com.expensetracker.smart_expense_tracker.dto.SignUpRequest;
import com.expensetracker.smart_expense_tracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        userService.registerUser(signUpRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginRequest loginRequest){
        String token = userService.login(loginRequest);

        Map<String,String> response = new HashMap<>();
        response.put("token",token);

        return ResponseEntity.ok(response);
    }
}
