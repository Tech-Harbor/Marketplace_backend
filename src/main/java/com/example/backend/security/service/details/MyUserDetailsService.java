package com.example.backend.security.service.details;

import com.example.backend.web.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.example.backend.utils.exception.RequestException.notFoundRequestException;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserDetailsFactory myUserDetailsFactory;
    private final UserService userService;

    @Override
    @SneakyThrows
    public MyUserDetails loadUserByUsername(final String username) {
        var user =  userService.getBySecurityEmail(username).orElseThrow(
                () -> notFoundRequestException("Email not found")
        );
        return myUserDetailsFactory.build(user);
    }
}