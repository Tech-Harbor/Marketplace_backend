package com.example.backend.web.User;

import com.example.backend.web.utils.WebIsNullFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserFactory implements Function<UserEntity, UserDTO> {

    private final WebIsNullFactory webIsNullFactory;

    @Override
    public UserDTO apply(final UserEntity user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .status(user.getRegisterAuthStatus())
                .product(webIsNullFactory.isNullProductUser(user))
                .role(user.getRole())
                .build();
    }
}