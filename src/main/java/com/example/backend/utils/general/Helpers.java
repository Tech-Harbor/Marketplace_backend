package com.example.backend.utils.general;


import com.example.backend.security.service.JwtService;
import com.example.backend.web.User.UserService;
import com.example.backend.web.User.store.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Helpers {

    private final JwtService jwtService;
    private final UserService userService;

    public UserEntity tokenUserData(final String jwt) {
        final var token = jwtService.extractUserData(jwt.substring(7));

        return userService.getByUserData(token);
    }

    public Optional<UserEntity> tokenUserEmail(final String jwt) {
        final var token = jwtService.extractUserData(jwt.substring(7));

        return userService.getByEmail(token);
    }
}