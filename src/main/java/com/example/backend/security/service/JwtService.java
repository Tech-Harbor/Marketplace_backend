package com.example.backend.security.service;

import com.example.backend.security.service.details.MyUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.function.Function;

public interface JwtService {
    String extractUserEmail(final String token);
    <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver);
    String generateAccessToken(final Authentication authentication);
    String generateRefreshToken(final Authentication authentication);
    boolean isTokenValid(final String token, final MyUserDetails userDetails);
}