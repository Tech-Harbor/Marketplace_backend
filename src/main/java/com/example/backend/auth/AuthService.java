package com.example.backend.auth;

import com.example.backend.Config.Security.JwtService;
import com.example.backend.User.UserDTO;
import com.example.backend.User.UserEntity;
import com.example.backend.User.UserServiceImpl;
import com.example.backend.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(UserDTO userDTO) {
        UserEntity user = UserEntity.builder()
                .firstname(userDTO.firstname())
                .lastname(userDTO.lastname())
                .email(userDTO.email())
                .number(userDTO.number())
                .password(passwordEncoder.encode(userDTO.password()))
                .role(Role.USER)
                .build();

        userService.createUser(user);
        String token = jwtService.generateJwtToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        UserEntity user = userService.getByEmail(authRequest.email());
        String token = jwtService.generateJwtToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}