package com.example.backend.security;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.JwtTokenService;
import com.example.backend.security.service.impl.AuthServiceImpl;
import com.example.backend.utils.general.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.UserServiceImpl;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Properties;

import static com.example.backend.utils.general.Constants.EMAIL_KEY;
import static com.example.backend.utils.general.Constants.PASSWORD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private MyPasswordEncoder myPasswordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private MailService mailService;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private JwtService jwtService;

    @Test
    void sigUpTest() {
        final var registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final var userCaptor = ArgumentCaptor.forClass(UserSecurityDTO.class);

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn(PASSWORD);

        authService.signup(registerRequest);

        verify(userService).getByEmail(any());
        verify(userService).mySecuritySave(any(UserEntity.class));
        verify(mailService).sendEmail(userCaptor.capture(), eq(MailType.REGISTRATION), any(Properties.class));
    }

    @Test
    void sigUpNotTest() {
        final var registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final var userCaptor = ArgumentCaptor.forClass(UserSecurityDTO.class);

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn(PASSWORD);

        authService.signup(registerRequest);

        verify(userService).getByEmail(EMAIL_KEY);
        verify(mailService).sendEmail(userCaptor.capture(), eq(MailType.REGISTRATION), any(Properties.class));
        verify(userService).mySecuritySave(any(UserEntity.class));

        verifyNoMoreInteractions(userService, userRepository, mailService);
    }

    @Test
    void loginTest() {
        final var authRequest = AuthRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final var authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenService.generateAccessToken(authentication)).thenReturn("AccessToken");
        when(jwtTokenService.generateRefreshToken(authentication)).thenReturn("RefreshToken");
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.of(mock(UserEntity.class)));

        final var authRequestLogin = authService.login(authRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenService).generateAccessToken(authentication);
        verify(jwtTokenService).generateRefreshToken(authentication);
        verify(userService).getByEmail(authRequest.email());

        assertNotNull(authRequestLogin.accessToken());
        assertNotNull(authRequestLogin.refreshToken());
    }

    @Test
    void loginNotTest() {
        final var authRequest = AuthRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final var authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> authService.login(authRequest));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).getByEmail(authRequest.email());

        verifyNoMoreInteractions(authenticationManager, userService, jwtTokenService);
    }

    @Test
    void formUpdatePasswordTest() {
        final var jwt = "Bearer some-jwt-token";

        final var passwordRequest = PasswordRequest.builder()
                .password(PASSWORD)
                .build();

        // Мок поведінки jwtService, щоб повернути певний користувацький ідентифікатор з jwt
        when(jwtService.extractUserData(jwt.substring(7))).thenReturn(EMAIL_KEY);

        // Мок поведінки userService, щоб повернути опціональний об'єкт з користувачем
        final var user = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(user));

        // Мок поведінки myPasswordEncoder, щоб повернути мокований PasswordEncoder
        final var encoder = mock(PasswordEncoder.class);
        when(myPasswordEncoder.passwordEncoder()).thenReturn(encoder);

        when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);

        // Виклик методу, який тестуємо
        authService.formUpdatePassword(jwt, passwordRequest);

        // Перевірка, що паролі закодовано та збережено
        assertEquals(PASSWORD, user.getPassword());

        verify(userService).mySecuritySave(user);
    }

    @Test
    void requestEmailUpdatePasswordTest() {
        final var emailRequest = EmailRequest.builder()
                .email(EMAIL_KEY)
                .build();

        final var userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        final var userSecurity = userService.mySecuritySave(userEntity);

        when(userService.getByEmail(emailRequest.email())).thenReturn(Optional.of(userEntity));

        authService.requestEmailUpdatePassword(emailRequest);

        verify(userService).getByEmail(emailRequest.email());
        verify(mailService).sendEmail(userSecurity, MailType.NEW_PASSWORD, new Properties());
    }

    @Test
    void activeUserTest() {
        final var userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        final var userOptional = Optional.of(userEntity);

        when(jwtService.extractUserData(anyString())).thenReturn(EMAIL_KEY);

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(userOptional);

        authService.activeUser("your_jwt_token");

        verify(jwtService).extractUserData(anyString());
        verify(userService).getByEmail(EMAIL_KEY);
        verify(userService).mySecuritySave(userEntity);
    }

    @Test
    void sendEmailActiveTest() {
        final var emailRequest = EmailRequest.builder()
                .email(EMAIL_KEY)
                .build();

        final var user = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        final var userCaptor = ArgumentCaptor.forClass(UserSecurityDTO.class);

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(user));

        authService.sendEmailActive(emailRequest);

        verify(userService).getByEmail(EMAIL_KEY);

        verify(mailService).sendEmail(userCaptor.capture(), eq(MailType.REGISTRATION), any(Properties.class));
    }
}