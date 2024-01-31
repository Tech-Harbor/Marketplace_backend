package com.example.backend.auth;

import com.example.backend.User.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final static String SIGNUP_URI = "/signup";
    private final static String LOGIN_URI = "/login";

    private final AuthService authService;

    @PostMapping(SIGNUP_URI)
    public AuthResponse signup(@RequestBody UserDTO userDTO) {
        return authService.signup(userDTO);
    }

    @PostMapping(LOGIN_URI)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}