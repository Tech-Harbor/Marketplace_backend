package com.example.backend.web.User.store.factory;

import com.example.backend.utils.general.WebIsNullFactory;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserWebSocketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserWebSocketFactory implements Function<UserEntity, UserWebSocketDTO> {

    private final WebIsNullFactory webIsNullFactory;

    @Override
    public UserWebSocketDTO apply(final UserEntity user) {
        return UserWebSocketDTO.builder()
                .firstname(user.getFirstname())
                .image(user.getImage().getImageUrl())
                .lastname(user.getLastname())
                .advertisements(webIsNullFactory.isNullAdvertisementUser(user))
                .build();
    }
}