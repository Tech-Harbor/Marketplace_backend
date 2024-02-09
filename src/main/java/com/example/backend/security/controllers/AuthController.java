package com.example.backend.security.controllers;

import com.example.backend.api.props.GoogleProperties;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final GoogleProperties googleProperties;

    private final static String SIGNUP_URI = "/signup";
    private final static String LOGIN_URI = "/login";
    private final static String INFO = "/info";
    private final static String GOOGLE = "/createUserGoogle";

    @PostMapping(SIGNUP_URI)
    public AuthResponse signup(@RequestBody @Validated RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    public AuthResponse login(@RequestBody @Validated AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping(GOOGLE)
    public Map<String, Object> createUserGoogle() {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(googleProperties.getClientId(), googleProperties.getClientSecret());
        return responseData;
    }

    @GetMapping(INFO)
    public String info(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }
}