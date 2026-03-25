package com.kodenca.ms_authentication.controller;

import com.kodenca.ms_authentication.dto.request.LoginRequest;
import com.kodenca.ms_authentication.dto.request.RegisterRequest;
import com.kodenca.ms_authentication.dto.response.BusinessResponse;
import com.kodenca.ms_authentication.dto.response.AuthResponse;
import com.kodenca.ms_authentication.service.contract.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final IAuthService authService;
    
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<BusinessResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(
                BusinessResponse.<AuthResponse>builder()
                        .success(true)
                        .message("User registered successfully")
                        .timestamp(LocalDateTime.now())
                        .data(response)
                        .build()
        );
    }
    
    @PostMapping("/login")
    public ResponseEntity<BusinessResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(
                BusinessResponse.<AuthResponse>builder()
                        .success(true)
                        .message("User logged in successfully")
                        .timestamp(LocalDateTime.now())
                        .data(response)
                        .build()
        );
    }
}
