package com.example.backend.security.service.details;

import com.example.backend.web.User.store.dto.UserSecurityDTO;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsFactory {
    public MyUserDetails build(final UserSecurityDTO user) {
        return MyUserDetails.builder()
                .user(UserSecurityDTO.builder()
                        .lastname(user.lastname())
                        .firstname(user.firstname())
                        .email(user.email())
                        .password(user.password())
                        .phone(user.phone())
                        .roles(user.roles())
                        .status(user.status())
                        .enabled(user.enabled())
                        .build())
                .build();
    }
}