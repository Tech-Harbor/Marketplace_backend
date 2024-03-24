package com.example.backend.security;

import com.example.backend.mail.MailService;
import com.example.backend.mail.MailType;
import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.impl.AuthServiceImpl;
import com.example.backend.security.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private JwtService jwtService;

    @Test
    void sigUpTest() {
        String email = "email";

        final RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(email)
                .password("password")
                .build();

        when(userService.getByEmail(email)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn("encodedPassword");

        authService.signup(registerRequest);

        verify(userService).getByEmail(any());
        verify(userService).mySave(any(UserEntity.class));
        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
    }

    @Test
    void sigUpNotTest() {
        final String email = "email";

        final RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email(email)
                .password("password")
                .build();

        when(userService.getByEmail(email)).thenReturn(Optional.empty());
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(myPasswordEncoder.passwordEncoder().encode(any())).thenReturn("encodedPassword");

        authService.signup(registerRequest);

        verify(userService).getByEmail(email);
        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
        verify(userService).mySave(any(UserEntity.class));

        verifyNoMoreInteractions(userService, userRepository, mailService);
    }

    @Test
    void loginTest() {
        final AuthRequest authRequest = AuthRequest.builder()
                .email("email")
                .password("password")
                .build();

        final Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateAccessToken(authentication)).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(authentication)).thenReturn("RefreshToken");
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.of(mock(UserEntity.class)));

        final AuthResponse authRequestLogin = authService.login(authRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateAccessToken(authentication);
        verify(jwtService).generateRefreshToken(authentication);
        verify(userService).getByEmail(authRequest.email());

        assertNotNull(authRequestLogin.accessToken());
        assertNotNull(authRequestLogin.refreshToken());
    }

    @Test
    void loginNotTest() {
        final AuthRequest authRequest = AuthRequest.builder()
                .email("email")
                .password("password")
                .build();

        final Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> authService.login(authRequest));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).getByEmail(authRequest.email());

        verifyNoMoreInteractions(authenticationManager, userService, jwtService);
    }

    @Test
    void formUpdatePasswordTest() {
        final Long userId = 1L;
        final String jwt = "jwt";

        final PasswordRequest passwordRequest = PasswordRequest.builder()
                .password("password")
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
        when(userService.getById(userId)).thenReturn(userEntity);

        authService.formUpdatePassword(jwt, passwordRequest);

        verify(myPasswordEncoder).passwordEncoder();
        verify(userService).getById(userId);
        verify(userService).mySave(userEntity);
    }

    @Test
    void formUpdatePasswordNotTest() {
        final Long userId = 1L;
        final String jwt = "jwt";

        final PasswordRequest passwordRequest = PasswordRequest.builder()
                .password("password")
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .password("password")
                .build();

        when(myPasswordEncoder.passwordEncoder()).thenReturn(new PasswordEncoderTestUtils());
        when(userService.getById(userId)).thenReturn(userEntity);

        authService.formUpdatePassword(jwt, passwordRequest);

        verify(myPasswordEncoder).passwordEncoder();
        verify(userService).getById(userId);
        verify(userService).mySave(userEntity);
    }

    @Test
    void requestEmailUpdatePasswordTest() {
        final Long userId = 1L;

        final EmailRequest emailRequest = EmailRequest.builder()
                .email("email")
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getByEmail(emailRequest.email())).thenReturn(Optional.of(userEntity));

        authService.requestEmailUpdatePassword(emailRequest);

        verify(userService).getByEmail(emailRequest.email());
        verify(mailService).sendEmail(userEntity, MailType.NEW_PASSWORD, new Properties());
    }

    private static final class PasswordEncoderTestUtils implements PasswordEncoder {
        @Override
        public String encode(final CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }
}