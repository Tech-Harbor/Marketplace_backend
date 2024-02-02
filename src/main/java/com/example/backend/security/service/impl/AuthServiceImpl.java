package com.example.backend.security.service.impl;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.auth.AuthRequest;
import com.example.backend.security.auth.AuthResponse;
import com.example.backend.security.service.AuthService;
import com.example.backend.security.service.JwtService;
import com.example.backend.web.User.Role;
import com.example.backend.web.User.UserDTO;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(UserDTO userDTO) {
        UserEntity user = UserEntity.builder()
                .firstname(userDTO.firstname())
                .lastname(userDTO.lastname())
                .email(userDTO.email())
                .number(userDTO.number())
                .password(passwordEncoder.encode(userDTO.password()))
                .role(Role.USER)
                .build();

        userService.createUser(user);

        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());

        String token = jwtService.generateJwtToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
                )
        );

        UserEntity user = userService.getByEmail(authRequest.email());

        String token = jwtService.generateJwtToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}