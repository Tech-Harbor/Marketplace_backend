package com.example.backend.security.service.impl;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.models.token.TokenEntity;
import com.example.backend.security.models.token.TokenRepository;
import com.example.backend.security.models.token.TokenType;
import com.example.backend.security.service.AuthService;
import com.example.backend.security.service.JwtService;
import com.example.backend.security.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.UserServiceImpl;
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
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
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

        userRepository.save(user);

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

        var user = userService.getByEmail(authRequest.email()).orElseThrow();

        var accessToken = jwtService.generateAccessToken(authentication);

        var refreshToken = jwtService.generateRefreshToken(authentication);

        saveUserToken(user, refreshToken);

        revokeAllUserTokens(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public void formUpdatePassword(Long id, PasswordRequest passwordRequest) {
        UserEntity userId = userRepository.getReferenceById(id);

        userId.setPassword(myPasswordEncoder.passwordEncoder().encode(passwordRequest.password()));

        userRepository.save(userId);
    }

    @Override
    public void requestEmailUpdatePassword(EmailRequest emailRequest) {
        UserEntity emailUser = userService.getByEmail(emailRequest.email()).orElse(null);

        mailService.sendEmail(emailUser, MailType.NEW_PASSWORD, new Properties());
    }

    private void saveUserToken(UserEntity user, String jwtToken) {

        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {

        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}