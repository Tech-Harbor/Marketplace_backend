package com.example.backend.utils.general;

import com.example.backend.utils.props.JwtProperties;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class SignInKey {

    private final JwtProperties jwtProperties;

    public final SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getKey()));
    }
}