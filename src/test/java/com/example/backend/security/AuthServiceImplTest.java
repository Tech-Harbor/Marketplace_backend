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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
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
    public void sigUpTest(){
        String email = "email";

        RegisterRequest registerRequest = RegisterRequest.builder()
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
        verify(userRepository).save(any(UserEntity.class));
        verify(mailService).sendEmail(any(UserEntity.class), eq(MailType.REGISTRATION), any(Properties.class));
    }

    @Test
    public void loginTest(){
        AuthRequest authRequest = AuthRequest.builder()
                .email("email")
                .password("password")
                .build();

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateAccessToken(authentication)).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(authentication)).thenReturn("RefreshToken");
        when(userService.getByEmail(authRequest.email())).thenReturn(Optional.of(mock(UserEntity.class)));

        AuthResponse authRequestLogin = authService.login(authRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateAccessToken(authentication);
        verify(jwtService).generateRefreshToken(authentication);
        verify(userService).getByEmail(authRequest.email());

        assertNotNull(authRequestLogin.accessToken());
        assertNotNull(authRequestLogin.refreshToken());
    }

    @Test
    public void formUpdatePasswordTest(){
        Long userId = 1L;

        PasswordRequest passwordRequest = PasswordRequest.builder()
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userRepository.getReferenceById(userId)).thenReturn(userEntity);
        when(myPasswordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));

        authService.formUpdatePassword(userId, passwordRequest);

        verify(userRepository).getReferenceById(userId);
        verify(myPasswordEncoder).passwordEncoder();
        verify(userRepository).save(userEntity);
    }

    @Test
    public void requestEmailUpdatePasswordTest(){
        Long userId = 1L;

        EmailRequest emailRequest = EmailRequest.builder()
                .email("email")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getByEmail(emailRequest.email())).thenReturn(Optional.of(userEntity));

        authService.requestEmailUpdatePassword(emailRequest);

        verify(userService).getByEmail(emailRequest.email());
        verify(mailService).sendEmail(userEntity, MailType.NEW_PASSWORD, new Properties());
    }
}