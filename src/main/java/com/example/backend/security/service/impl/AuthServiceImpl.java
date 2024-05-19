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
import com.example.backend.security.service.JwtTokenService;
import com.example.backend.utils.general.MyPasswordEncoder;
import com.example.backend.web.User.UserService;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import com.example.backend.web.User.store.factory.UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Properties;

import static com.example.backend.utils.enums.RegisterAuthStatus.JWT;
import static com.example.backend.utils.enums.Role.USER;
import static com.example.backend.utils.enums.Status.OFFLINE;
import static com.example.backend.utils.exception.RequestException.badRequestException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyPasswordEncoder myPasswordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UserInfoFactory userInfoFactory;
    private final UserService userService;
    private final MailService mailService;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void signup(final RegisterRequest registerRequest) {
        final var existUser = userService.getByEmail(registerRequest.email());

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
                .role(USER)
                .status(OFFLINE)
                .build();

        final var userSecurityDTO = userService.mySecuritySave(user);

        log.info("Register User: {}", userSecurityDTO);

        mailService.sendEmail(userSecurityDTO, MailType.REGISTRATION, new Properties());
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

        userService.getByEmail(authRequest.email()).orElseThrow();

        final var accessToken = jwtTokenService.generateAccessToken(authentication);

        final var refreshToken = jwtTokenService.generateRefreshToken(authentication);

        log.info("Login user: {}", authentication);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void formUpdatePassword(final String jwt, final PasswordRequest passwordRequest) {
        final var token = jwtService.extractUserData(jwt.substring(7));

        final var userPassword = userService.getByEmail(token);

        userPassword.ifPresent(user -> {
                user.setPassword(myPasswordEncoder.passwordEncoder().encode(passwordRequest.password()));

                log.info("Update Password: {}", user.getFirstname());

                userService.mySecuritySave(user);
            }
        );
    }

    @Override
    public void requestEmailUpdatePassword(final EmailRequest emailRequest) {
        final var emailUser = userService.getByEmail(emailRequest.email()).orElseThrow(
                () -> badRequestException("This email is not exists")
        );

        final var userSecurityDTO = userService.mySecuritySave(emailUser);

        log.info("Email user: {}", emailUser.getFirstname());

        mailService.sendEmail(userSecurityDTO, MailType.NEW_PASSWORD, new Properties());
    }

    @Override
    @Transactional
    public void activeUser(final String jwt) {
        final var token = jwtService.extractUserData(jwt.substring(7));

        final var activeUserTrue = userService.getByEmail(token);

        activeUserTrue.ifPresent(user -> {
                user.setEnabled(true);

                log.info("Active user: {}", user.getFirstname());

                userService.mySecuritySave(user);
            }
        );
    }

    @Override
    public void sendEmailActive(final EmailRequest emailRequest) {
        final var user = userService.getByEmail(emailRequest.email());

        user.ifPresent(entity -> {
                final var userSecurityDTO = userService.mySecuritySave(entity);

                log.info("SendEmail user: {}", entity.getFirstname());

                mailService.sendEmail(userSecurityDTO, MailType.REGISTRATION, new Properties());
            }
        );
    }

    @Override
    public UserInfoDTO profileUser(final String accessToken) {
        final var token = jwtService.extractUserData(accessToken.substring(7));

        final var user = userService.getByUserData(token);

        log.info("Info {}", user);

        return userInfoFactory.apply(user);
    }
}