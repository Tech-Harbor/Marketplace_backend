package com.example.backend.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtAuthServerFilter {
    void updateRefreshTokenFilter(HttpServletRequest request, HttpServletResponse response);
}