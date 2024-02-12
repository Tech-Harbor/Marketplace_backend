package com.example.backend.security.controllers;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "AuthSystem", description = "Точка входу в систему і особистий кабінет, користувача")
public class AuthController {

    private final AuthService authService;

    private final static String SIGNUP_URI = "/signup";
    private final static String LOGIN_URI = "/login";
    private final static String FORM_UPDATE_PASSWORD_URI = "/update/password/{userId}";
    private final static String REQUEST_EMAIL_UPDATE_PASSWORD = "/request/email";
    private final static String INFO = "/info";

    @PostMapping(SIGNUP_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    public AuthResponse signup(@RequestBody @Validated RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    public AuthResponse login(@RequestBody @Validated AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PatchMapping(FORM_UPDATE_PASSWORD_URI)
    public void updatePassword(@PathVariable Long userId, @RequestBody @Validated PasswordRequest passwordRequest) {
        authService.formUpdatePassword(userId, passwordRequest);
        // TODO: 12.02.2024 Треба перейти в сервер авторизації
    }

    @GetMapping(INFO)
    public String info(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }

    @PostMapping(REQUEST_EMAIL_UPDATE_PASSWORD)
    public void requestEmailUpdatePassword(@RequestBody @Validated EmailRequest emailRequest){
        authService.requestEmailUpdatePassword(emailRequest);
    }
}