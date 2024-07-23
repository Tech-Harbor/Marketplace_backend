package com.example.backend.security.servers.details;

import com.example.backend.web.User.UserServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.example.backend.utils.exception.RequestException.notFoundRequestException;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserDetailsFactory myUserDetailsFactory;
    private final UserServer userServer;

    @Override
    @SneakyThrows
    public MyUserDetails loadUserByUsername(final String username) {
        var user =  userServer.getBySecurityEmail(username).orElseThrow(
                () -> notFoundRequestException("Email not found")
        );
        return myUserDetailsFactory.build(user);
    }
}