package com.example.backend.security.servers.details;

import com.example.backend.web.User.store.dto.UserSecurityDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MyUserDetailsFactory implements Function<UserSecurityDTO, MyUserDetails> {
    @Override
    public MyUserDetails apply(final UserSecurityDTO user) {
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
                        .accountNonLocked(user.accountNonLocked())
                        .accountNonExpired(user.accountNonExpired())
                        .credentialsNonExpired(user.credentialsNonExpired())
                        .build())
                .build();
    }
}