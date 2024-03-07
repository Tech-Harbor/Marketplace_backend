package com.example.backend.security.service.details;

import com.example.backend.web.User.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsFactory {
    public MyUserDetails build(final UserEntity userEntity) {
        return MyUserDetails.builder()
                .user(UserEntity.builder()
                        .lastname(userEntity.getLastname())
                        .firstname(userEntity.getFirstname())
                        .email(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .phone(userEntity.getPhone())
                        .role(userEntity.getRole())
                        .registerAuthStatus(userEntity.getRegisterAuthStatus())
                        .build())
                .build();
    }
}