package com.example.backend.security.service.impl;

import com.example.backend.security.service.JwtTokenService;
import com.example.backend.security.service.details.MyUserDetails;
import com.example.backend.utils.general.JwtPropertiesManager;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
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
    public String generateUserPasswordDataToken(final UserSecurityDTO userData) {
        return generateJwtPasswordToken(userData);
    }

    @Override
    public String generateUserEmailDataToken(final UserSecurityDTO userData) {
        return generateJwtEmailToken(userData);
    }

    private String generateJwtPasswordToken(final UserSecurityDTO userData) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put(PASSWORD, userData.password());
        claims.put(ROLE, userData.role());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(claims)
                .subject(userData.email())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtUserDataExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtEmailToken(final UserSecurityDTO userData) {
        final Map<String, Object> role = new HashMap<>();

        role.put(ROLE, userData.role());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(role)
                .subject(userData.email())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtUserDataExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtAccessToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        final var userDetails = (MyUserDetails) authentication.getPrincipal();
        final Map<String, Object> role = new HashMap<>();

        role.put(ROLE, userDetails.user().role().name());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(role)
                .claims(extraClaims)
                .subject(authentication.getName())
                .issuedAt(DATE_TIME_MILLIS)
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtAccessExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }

    private String generateJwtRefreshToken(final Map<String, Object> extraClaims, final Authentication authentication) {
        final var userDetails = (MyUserDetails) authentication.getPrincipal();
        final Map<String, Object> role = new HashMap<>();

        role.put(ROLE, userDetails.user().role().name());

        return Jwts
                .builder()
                .header()
                .add(TYPE, JWT)
                .and()
                .claims(role)
                .claims(extraClaims)
                .subject(authentication.getName())
                .expiration(new Date(System.currentTimeMillis()
                        + jwtPropertiesManager.jwtProperties().getJwtRefreshExpiration().toMillis()))
                .signWith(jwtPropertiesManager.getSignInKey())
                .compact();
    }
}