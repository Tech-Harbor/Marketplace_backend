package com.example.backend.web.User.store.factory;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserImageUpdateInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserImageUpdateInfoFactory implements Function<UserEntity, UserImageUpdateInfoDTO> {
    @Override
    public UserImageUpdateInfoDTO apply(final UserEntity user) {
        return UserImageUpdateInfoDTO.builder()
                .image(user.getImage().getImageUrl())
                .build();
    }
}