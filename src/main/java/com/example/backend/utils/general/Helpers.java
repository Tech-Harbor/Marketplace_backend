package com.example.backend.utils.general;


import com.example.backend.security.servers.JwtServer;
import com.example.backend.web.User.UserServer;
import com.example.backend.web.User.store.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Helpers {

    private final JwtServer jwtServer;
    private final UserServer userServer;

    public Helpers(final @Lazy JwtServer jwtServer,
                   final @Lazy UserServer userServer) {
        this.jwtServer = jwtServer;
        this.userServer = userServer;
    }


    public UserEntity tokenUserData(final String jwt) {
        final var token = jwtServer.extractUserData(jwt.substring(7));

        return userServer.getByUserData(token);
    }

    public Optional<UserEntity> tokenUserEmail(final String jwt) {
        final var token = jwtServer.extractUserData(jwt.substring(7));

        return userServer.getByEmail(token);
    }
}