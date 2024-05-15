package com.example.backend.security.models.request;

import lombok.Builder;

@Builder
public record GoogleTokenRequest(String token) { }