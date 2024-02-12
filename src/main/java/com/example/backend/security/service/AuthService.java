package com.example.backend.security.service;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    void formUpdatePassword(Long id, PasswordRequest passwordRequest);
    void requestEmailUpdatePassword(EmailRequest emailRequest);
}
