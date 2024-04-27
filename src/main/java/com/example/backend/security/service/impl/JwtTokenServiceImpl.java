package com.example.backend.security.service.impl;

import com.example.backend.security.service.JwtTokenService;
import com.example.backend.utils.general.SignInKey;
import com.example.backend.utils.props.JwtProperties;
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

    private final JwtProperties jwtProperties;
    private final SignInKey signInKey;

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

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(claims)
                .subject(userData.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtUserDataExpiration().toMillis()))
                .signWith(signInKey.getSignInKey())
                .compact();
    }

    private String generateJwtEmailToken(final UserEntity userData) {
        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .subject(userData.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtUserDataExpiration().toMillis()))
                .signWith(signInKey.getSignInKey())
                .compact();
    }

    private String generateJwtAccessToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .header()
                .add(TYPE, JWT)
                .and()
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtAccessExpiration().toMillis()))
                .signWith(signInKey.getSignInKey())
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
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getJwtRefreshExpiration().toMillis()))
                .signWith(signInKey.getSignInKey())
                .compact();
    }
}