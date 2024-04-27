package com.example.backend.security;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.JwtTokenService;
import com.example.backend.security.service.impl.AuthServiceImpl;
import com.example.backend.utils.general.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.UserServiceImpl;
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

import static com.example.backend.utils.general.Constants.*;
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

    @Test
    void sigUpTest() {
        final RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn(PASSWORD);

        authService.signup(registerRequest);

        verify(userService).getByEmail(any());
        verify(userService).mySave(any(UserEntity.class));
        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
    }

    @Test
    void sigUpNotTest() {
        final RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn(PASSWORD);

        authService.signup(registerRequest);

        verify(userService).getByEmail(EMAIL_KEY);
        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
        verify(userService).mySave(any(UserEntity.class));

        verifyNoMoreInteractions(userService, userRepository, mailService);
    }

    @Test
    void loginTest() {
        final AuthRequest authRequest = AuthRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenService.generateAccessToken(authentication)).thenReturn("AccessToken");
        when(jwtTokenService.generateRefreshToken(authentication)).thenReturn("RefreshToken");
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.of(mock(UserEntity.class)));

        final AuthResponse authRequestLogin = authService.login(authRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenService).generateAccessToken(authentication);
        verify(jwtTokenService).generateRefreshToken(authentication);
        verify(userService).getByEmail(authRequest.email());

        assertNotNull(authRequestLogin.accessToken());
        assertNotNull(authRequestLogin.refreshToken());
    }

    @Test
    void loginNotTest() {
        final AuthRequest authRequest = AuthRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final Authentication authentication = mock(Authentication.class);

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
        final PasswordRequest passwordRequest = PasswordRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        final ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(userEntity));
        when(myPasswordEncoder.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        authService.formUpdatePassword(JWT, passwordRequest);


        verify(userService).mySave(userCaptor.capture());

        UserEntity capturedUser = userCaptor.getValue();
        assertEquals(PASSWORD, capturedUser.getPassword());
    }

    @Test
    void formUpdatePasswordNotTest() {
        final PasswordRequest passwordRequest = PasswordRequest.builder()
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        when(userService.getByEmail(eq(EMAIL_KEY))).thenReturn(Optional.of(userEntity));
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn(PASSWORD);

        authService.formUpdatePassword(JWT, passwordRequest);

        verify(userService).getByEmail(eq(EMAIL_KEY));
        verify(myPasswordEncoder.passwordEncoder()).encode(eq(PASSWORD));
        verify(userService).mySave(eq(userEntity));
    }

    @Test
    void requestEmailUpdatePasswordTest() {
        final Long userId = 1L;

        final EmailRequest emailRequest = EmailRequest.builder()
                .email(EMAIL_KEY)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getByEmail(emailRequest.email())).thenReturn(Optional.of(userEntity));

        authService.requestEmailUpdatePassword(emailRequest);

        verify(userService).getByEmail(emailRequest.email());
        verify(mailService).sendEmail(userEntity, MailType.NEW_PASSWORD, new Properties());
    }

    @Test
    void activeUserTest() {
        final EmailRequest emailRequest = EmailRequest.builder()
                .email(EMAIL_KEY)
                .build();

        final UserEntity userNotActive = UserEntity.builder()
                .email(EMAIL_KEY)
                .enabled(false)
                .build();

        final UserEntity userActive = UserEntity.builder()
                .email(EMAIL_KEY)
                .enabled(true)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(userNotActive));
        when(userService.mySave(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        authService.activeUser(JWT, emailRequest);

        assertTrue(userNotActive.getEnabled());
        assertTrue(userActive.getEnabled());
    }

    @Test
    void sendEmailActiveTest() {
        final EmailRequest emailRequest = EmailRequest.builder()
                .email(EMAIL_KEY)
                .build();

        final UserEntity user = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(user));

        authService.sendEmailActive(emailRequest);

        verify(userService).getByEmail(EMAIL_KEY);

        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
    }
}