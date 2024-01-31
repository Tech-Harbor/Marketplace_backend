package com.example.backend.auth;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}