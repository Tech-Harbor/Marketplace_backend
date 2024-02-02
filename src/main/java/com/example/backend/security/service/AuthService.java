package com.example.backend.security.service;

import com.example.backend.security.auth.request.AuthRequest;
import com.example.backend.security.auth.response.AuthResponse;
import com.example.backend.security.auth.request.RegisterRequest;

public interface AuthService {

    AuthResponse signup(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
}
