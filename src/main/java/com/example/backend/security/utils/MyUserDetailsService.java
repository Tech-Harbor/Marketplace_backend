package com.example.backend.security.utils;

import com.example.backend.web.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Email not found")
        );
    }
}
