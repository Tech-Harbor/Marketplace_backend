package com.example.backend.security.servers.impl;

import com.example.backend.security.servers.JwtServer;
import com.example.backend.security.servers.details.MyUserDetails;
import com.example.backend.utils.general.JwtPropertiesManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServerImpl implements JwtServer {

    private final JwtPropertiesManager jwtPropertiesManager;

    @Override
    public String extractUserData(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(final String token, final MyUserDetails userDetails) {
        return extractUserData(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
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
                .verifyWith(jwtPropertiesManager.getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}