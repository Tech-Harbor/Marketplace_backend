package com.example.backend.web.User.store.factory;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserSecurityFactory implements Function<UserEntity, UserSecurityDTO> {
    @Override
    public UserSecurityDTO apply(final UserEntity user) {
        return UserSecurityDTO.builder()
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .status(user.getRegisterAuthStatus())
                .roles(user.getRoles())
                .enabled(user.getEnabled())
                .build();
    }
}