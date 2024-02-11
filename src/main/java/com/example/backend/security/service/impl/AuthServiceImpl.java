package com.example.backend.security.service.impl;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.utils.MyPasswordEncoder;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import com.example.backend.security.service.JwtService;
import com.example.backend.web.User.*;
import com.example.backend.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final MyPasswordEncoder myPasswordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public AuthResponse signup(RegisterRequest registerRequest) {
        UserEntity existUser = userService.getByEmail(registerRequest.email()).orElse(null);

        if (existUser != null) {
            throw new BadRequestException("This email has already been used.");
        }

        UserEntity user = UserEntity.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .email(registerRequest.email())
                .password(myPasswordEncoder.passwordEncoder().encode(registerRequest.password()))
                .number(registerRequest.number())
                .registerAuthStatus(RegisterAuthStatus.JWT)
                .role(Role.USER)
                .build();

        userRepository.save(user);

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

        UserEntity user = userService.getByEmail(authRequest.email()).orElseThrow();

        String token = jwtService.generateJwtToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}