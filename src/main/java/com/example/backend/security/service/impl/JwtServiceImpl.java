package com.example.backend.security.service.impl;

import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.details.MyUserDetails;
import com.example.backend.utils.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String extractUserEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateAccessToken(final Authentication authentication) {
        return generateJwtAccessToken(new HashMap<>(), authentication);
    }

    @Override
    public String generateRefreshToken(final Authentication authentication) {
        return generateJwtRefreshToken(new HashMap<>(), authentication);
    }

    @Override
    public String generateNewPasswordTokenAndActiveUser(final String email) {
        return generateJwtNewPasswordTokenAndActiveUser(email);
    }

    private String generateJwtNewPasswordTokenAndActiveUser(final String email) {
        return Jwts
                .builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +
                        jwtProperties.getJwtNewPasswordExpirationAndActiveUser()))
                .signWith(getSignInKey())
                .compact();
    }

    private String generateJwtAccessToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtAccessExpiration()))
                .signWith(getSignInKey())
                .compact();
    }

    private String generateJwtRefreshToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(authentication.getName())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtRefreshExpiration()))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isTokenValid(final String token, final MyUserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
