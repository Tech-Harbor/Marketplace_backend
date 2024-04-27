package com.example.backend.security.service.impl;

import com.example.backend.security.service.JwtTokenService;
import com.example.backend.utils.general.JwtPropertiesManager;
import com.example.backend.web.User.UserEntity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.backend.utils.general.Constants.*;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtPropertiesManager jwtPropertiesManager;

    @Override
    public String generateAccessToken(final Authentication authentication) {
        return generateJwtAccessToken(new HashMap<>(), authentication);
    }

    @Override
    public String generateRefreshToken(final Authentication authentication) {
        return generateJwtRefreshToken(new HashMap<>(), authentication);
    }

    @Override
    public String generateUserPasswordDataToken(final UserEntity userData) {
        return generateJwtPasswordToken(userData);
    }

    @Override
    public String generateUserEmailDataToken(final UserEntity userData) {
        return generateJwtEmailToken(userData);
    }

    private String generateJwtPasswordToken(final UserEntity userData) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(PASSWORD, userData.getPassword());
        claims.put(ROLE, userData.getRole());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(claims)
                .subject(userData.getEmail())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtUserDataExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtEmailToken(final UserEntity userData) {
        Map<String, Object> role = new HashMap<>();
        role.put(ROLE, userData.getRole());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(role)
                .subject(userData.getEmail())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtUserDataExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtAccessToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(extraClaims)
                .subject(authentication.getName())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtAccessExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtRefreshToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(extraClaims)
                .subject(authentication.getName())
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtRefreshExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }
}