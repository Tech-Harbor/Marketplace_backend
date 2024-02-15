package com.example.backend.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String extractUserEmail(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateJwtToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
