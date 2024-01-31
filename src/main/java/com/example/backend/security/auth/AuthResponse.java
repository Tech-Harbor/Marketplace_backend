package com.example.backend.security.auth;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}