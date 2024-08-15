package com.example.backend.utils.general;

import com.example.backend.security.servers.JwtServer;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.store.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.backend.utils.exception.RequestException.badRequestException;

@Component
@RequiredArgsConstructor
public class Helpers {

    private final UserRepository userRepository;
    private final JwtServer jwtServer;

    public UserEntity tokenUserData(final String jwt) {
        final var token = jwtServer.extractUserData(jwt.substring(7));

        return getByUserData(token);
    }

    public Optional<UserEntity> tokenUserEmail(final String jwt) {
        final var token = jwtServer.extractUserData(jwt.substring(7));

        return userRepository.findByEmail(token);
    }

    private UserEntity getByUserData(final String userData) {
        return userRepository.findByEmail(userData).orElseThrow(
                () -> badRequestException("Not userData: " + userData)
        );
    }
}