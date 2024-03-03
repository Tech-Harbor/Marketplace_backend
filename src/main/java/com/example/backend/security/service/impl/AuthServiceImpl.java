package com.example.backend.security.service.impl;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import com.example.backend.security.service.JwtService;
import com.example.backend.security.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import com.example.backend.web.User.utils.RegisterAuthStatus;
import com.example.backend.web.User.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

import static com.example.backend.web.exception.RequestException.badRequestException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyPasswordEncoder myPasswordEncoder;
    private final UserService userService;
    private final MailService mailService;
    private final JwtService jwtService;

    @Override
    public void signup(RegisterRequest registerRequest) {
        Optional<UserEntity> existUser = userService.getByEmail(registerRequest.email());

        existUser.ifPresent(user -> {throw badRequestException("This email has already been used.");});

        UserEntity user = UserEntity.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .email(registerRequest.email())
                .password(myPasswordEncoder.passwordEncoder().encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .registerAuthStatus(RegisterAuthStatus.JWT)
                .role(Role.USER)
                .build();

        userService.mySave(user);

        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.getByEmail(authRequest.email()).orElseThrow();

        var accessToken = jwtService.generateAccessToken(authentication);

        var refreshToken = jwtService.generateRefreshToken(authentication);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void formUpdatePassword(Long id, PasswordRequest passwordRequest) {
        UserEntity userId = userService.getById(id);

        userId.setPassword(myPasswordEncoder.passwordEncoder().encode(passwordRequest.password()));

        userService.mySave(userId);
    }

    @Override
    public void requestEmailUpdatePassword(EmailRequest emailRequest) {
        UserEntity emailUser = userService.getByEmail(emailRequest.email()).orElse(null);

        mailService.sendEmail(emailUser, MailType.NEW_PASSWORD, new Properties());
    }
}