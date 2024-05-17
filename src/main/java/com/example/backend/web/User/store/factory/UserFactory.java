package com.example.backend.web.User.store.factory;

import com.example.backend.utils.general.WebIsNullFactory;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserDTO;
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
                .image(user.getImage().getImageUrl())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .advertisements(webIsNullFactory.isNullAdvertisementUser(user))
                .build();
    }
}