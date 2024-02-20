package com.example.backend.security.service.details;

import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserDetailsFactory myUserDetailsFactory;
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        UserEntity user =  userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Email not found")
        );
        return myUserDetailsFactory.build(user);
    }
}
