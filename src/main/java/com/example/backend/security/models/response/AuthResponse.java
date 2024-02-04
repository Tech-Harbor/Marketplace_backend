package com.example.backend.security.models.response;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}