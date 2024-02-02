package com.example.backend.security.auth.response;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}