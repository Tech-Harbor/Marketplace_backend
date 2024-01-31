package com.example.backend.security.service;

import com.example.backend.security.auth.AuthRequest;
import com.example.backend.security.auth.AuthResponse;
import com.example.backend.web.User.UserDTO;

public interface AuthService {

    AuthResponse signup(UserDTO userDTO);
    AuthResponse login(AuthRequest authRequest);
}
