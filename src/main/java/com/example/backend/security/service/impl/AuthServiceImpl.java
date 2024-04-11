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
import com.example.backend.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

import static com.example.backend.web.exception.RequestException.badRequestException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyPasswordEncoder myPasswordEncoder;
    private final UserService userService;
    private final MailService mailService;
    private final JwtService jwtService;

    @Override
    public void signup(final RegisterRequest registerRequest) {
        final Optional<UserEntity> existUser = userService.getByEmail(registerRequest.email());

        existUser.ifPresent(user -> {
                throw badRequestException("This email has already been used.");
            }
        );

        final UserEntity user = UserEntity.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .email(registerRequest.email())
                .password(myPasswordEncoder.passwordEncoder().encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .registerAuthStatus(RegisterAuthStatus.JWT)
                .enabled(false)
                .role(Role.USER)
                .build();

        userService.mySave(user);

        log.info("Register User: " + user);

        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
    }

    @Override
    public AuthResponse login(final AuthRequest authRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.getByEmail(authRequest.email()).orElseThrow();

        final var accessToken = jwtService.generateAccessToken(authentication);

        final var refreshToken = jwtService.generateRefreshToken(authentication);

        log.info("Login user: " + authentication);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void formUpdatePassword(final String jwt, final PasswordRequest passwordRequest) {
        final var id = 1L;

        final UserEntity userId = userService.getById(id);

        userId.setPassword(myPasswordEncoder.passwordEncoder().encode(passwordRequest.password()));

        log.info("Update Password :" + userId.getLastname());

        userService.mySave(userId);
    }

    @Override
    public void requestEmailUpdatePassword(final EmailRequest emailRequest) {
        final UserEntity emailUser = userService.getByEmail(emailRequest.email()).orElseThrow(
                () -> badRequestException("This email is not exists")
        );

        log.info("Email user" + emailUser.getEmail());

        mailService.sendEmail(emailUser, MailType.NEW_PASSWORD, new Properties());
    }

    @Override
    public void activeUser(final String jwt) {
        final var id = 1L;

        final UserEntity activeUserTrue = userService.getById(id);

        activeUserTrue.setEnabled(true);

        log.info("Active user: " + activeUserTrue.getLastname());

        userService.mySave(activeUserTrue);
    }

    @Override
    public void sendEmailActive() {
        final var id = 1L;

        final var user = userService.getById(id);

        log.info("SendEmail user: " + user.getLastname());

        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
    }
}