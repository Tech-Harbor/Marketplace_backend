package com.example.backend.web.User.store.factory;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserUpdateInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserUpdateInfoFactory implements Function<UserEntity, UserUpdateInfoDTO> {
    @Override
    public UserUpdateInfoDTO apply(final UserEntity user) {
        return UserUpdateInfoDTO.builder()
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
    }
}