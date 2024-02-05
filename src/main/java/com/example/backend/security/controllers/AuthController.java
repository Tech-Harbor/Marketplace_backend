package com.example.backend.security.controllers;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final static String SIGNUP_URI = "/signup";
    private final static String LOGIN_URI = "/login";
    private final static String INFO = "/info";

    private final AuthService authService;

    @PostMapping(SIGNUP_URI)
    public AuthResponse signup(@RequestBody RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping(INFO)
    public String info(Principal principal){
        return principal.getName();
    }
}