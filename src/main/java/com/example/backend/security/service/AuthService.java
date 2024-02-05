package com.example.backend.security.service;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.models.request.RegisterRequest;

public interface AuthService {

    AuthResponse signup(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
}
