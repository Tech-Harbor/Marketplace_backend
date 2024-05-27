package com.example.backend.web.User.store.factory;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserInfoFactory implements Function<UserEntity, UserInfoDTO> {
    @Override
    public UserInfoDTO apply(final UserEntity user) {
        return UserInfoDTO.builder()
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .image(user.getImage().getImageUrl())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .status(user.getStatus())
                .password(user.getPassword())
                .build();
    }
}