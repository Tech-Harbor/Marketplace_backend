package com.example.backend.security.auth;

import com.example.backend.security.service.AuthService;
import com.example.backend.web.User.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SIGNUP_URI = "/signup";
    private static final String LOGIN_URI = "/login";
    private static final String INFO = "/info";

    private final AuthService authService;

    @PostMapping(SIGNUP_URI)
    public AuthResponse signup(@RequestBody UserDTO userDTO) {
        return authService.signup(userDTO);
    }

    @PostMapping(LOGIN_URI)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping(INFO)
    public String info(Principal principal){
        return principal.getName();
    }
}