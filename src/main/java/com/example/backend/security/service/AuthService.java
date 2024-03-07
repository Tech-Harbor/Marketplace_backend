package com.example.backend.security.service;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;

public interface AuthService {
    void signup(final RegisterRequest registerRequest);
    AuthResponse login(final AuthRequest authRequest);
    void formUpdatePassword(final Long id, final PasswordRequest passwordRequest);
    void requestEmailUpdatePassword(final EmailRequest emailRequest);
}