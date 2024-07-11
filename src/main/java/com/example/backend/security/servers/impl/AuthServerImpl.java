package com.example.backend.security.servers.impl;

import com.example.backend.mail.MailServer;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.servers.AuthServer;
import com.example.backend.security.servers.JwtTokenServer;
import com.example.backend.utils.general.Helpers;
import com.example.backend.utils.general.MyPasswordEncoder;
import com.example.backend.web.User.UserServer;
import com.example.backend.web.User.store.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Set;

import static com.example.backend.utils.enums.RegisterAuthStatus.JWT;
import static com.example.backend.utils.enums.Role.ADMIN;
import static com.example.backend.utils.enums.Role.USER;
import static com.example.backend.utils.exception.RequestException.badRequestException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServerImpl implements AuthServer {

    private final AuthenticationManager authenticationManager;
    private final MyPasswordEncoder myPasswordEncoder;
    private final JwtTokenServer jwtTokenServer;
    private final UserServer userServer;
    private final MailServer mailServer;
    private final Helpers helpers;

    @Override
    @Transactional
    public void signup(final RegisterRequest registerRequest) {
        final var existUser = userServer.getByEmail(registerRequest.email());

        existUser.ifPresent(user -> {
                throw badRequestException("This email has already been used.");
            }
        );

        final var user = UserEntity.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .email(registerRequest.email())
                .password(myPasswordEncoder.passwordEncoder().encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .registerAuthStatus(JWT)
                .enabled(false)
                .roles(Set.of(USER, ADMIN))
                .build();

        final var userSecurityDTO = userServer.mySecuritySave(user);

        log.info("Register User: {}", userSecurityDTO);

        mailServer.sendEmail(userSecurityDTO, MailType.REGISTRATION, new Properties());
    }

    @Override
    public AuthResponse login(final AuthRequest authRequest) {
        final var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userServer.getByEmail(authRequest.email()).orElseThrow();

        final var accessToken = jwtTokenServer.generateAccessToken(authentication);

        final var refreshToken = jwtTokenServer.generateRefreshToken(authentication);

        log.info("Login user: {}", authentication);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void formUpdatePassword(final String jwt, final PasswordRequest passwordRequest) {
        final var userPassword = helpers.tokenUserEmail(jwt);

        userPassword.ifPresent(user -> {
                user.setPassword(myPasswordEncoder.passwordEncoder().encode(passwordRequest.password()));

                log.info("Update Password: {}", user.getFirstname());

                final var userSecurityDTO = userServer.mySecuritySave(user);

                mailServer.sendEmail(userSecurityDTO, MailType.UPDATED_PASSWORD, new Properties());
            }
        );
    }

    @Override
    public void requestEmailUpdatePassword(final EmailRequest emailRequest) {
        final var emailUser = userServer.getByEmail(emailRequest.email()).orElseThrow(
                () -> badRequestException("This email is not exists")
        );

        final var userSecurityDTO = userServer.mySecuritySave(emailUser);

        log.info("Email user: {}", emailUser.getFirstname());

        mailServer.sendEmail(userSecurityDTO, MailType.NEW_PASSWORD, new Properties());
    }

    @Override
    @Transactional
    public void activeUser(final String jwt) {
        final var activeUserTrue = helpers.tokenUserEmail(jwt);

        activeUserTrue.ifPresent(user -> {
                user.setEnabled(true);

                log.info("Active user: {}", user.getFirstname());

                userServer.mySecuritySave(user);
            }
        );
    }

    @Override
    public void sendEmailActive(final EmailRequest emailRequest) {
        final var user = userServer.getByEmail(emailRequest.email());

        user.ifPresent(entity -> {
                final var userSecurityDTO = userServer.mySecuritySave(entity);

                log.info("SendEmail user: {}", entity.getFirstname());

                mailServer.sendEmail(userSecurityDTO, MailType.REGISTRATION, new Properties());
            }
        );
    }
}