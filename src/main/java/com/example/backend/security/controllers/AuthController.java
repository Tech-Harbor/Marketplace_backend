package com.example.backend.security.controllers;

import com.example.backend.security.models.request.*;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import com.example.backend.security.service.GoogleService;
import com.example.backend.utils.annotations.*;
import com.example.backend.web.User.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
@Tag(name = "Authentication", description = "Authentication User and Update Password, personal office users")
public class AuthController {

    private final AuthService authService;
    private final GoogleService googleService;

    private static final String SIGNUP_URI = "/auth/signup";
    private static final String LOGIN_URI = "/auth/login";
    private static final String GOOGLE_LOGIN = "/auth/google/login";
    private static final String FORM_CHANGE_PASSWORD_URI = "/change-password";
    private static final String REQUEST_EMAIL_UPDATE_PASSWORD = "/request/email";
    private static final String ACTIVE_USER = "/active";
    private static final String INFO = "/accouth";
    private static final String SEND_MESSAGE_EMAIL_NOT_ACTIVE = "/sendMessageEmailActive";

    @PostMapping(SIGNUP_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Register user")
    @ApiResponseOK
    @ApiResponseBadRequest
    public void signup(@RequestBody @Validated final RegisterRequest registerRequest) {
        authService.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Login user")
    @ApiResponseOK
    @ApiResponseUnauthorized
    @ApiResponseForbidden
    public AuthResponse login(@RequestBody @Validated final AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PutMapping(FORM_CHANGE_PASSWORD_URI)
    @Operation(summary = "Update Password User")
    @ApiResponseOK
    @ApiResponseNotFound
    @ApiResponseBadRequest
    public void updatePassword(@RequestHeader(AUTHORIZATION) final String jwt,
                               @RequestBody @Validated final PasswordRequest passwordRequest) {
        authService.formUpdatePassword(jwt, passwordRequest);
    }

    @GetMapping(INFO)
    @Operation(summary = "Information about the user who is authorized and logged into the system")
    @ApiResponseUnauthorized
    public String info(@AuthenticationPrincipal final UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @PostMapping(REQUEST_EMAIL_UPDATE_PASSWORD)
    @Operation(summary = "Change password using email")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void requestEmailUpdatePassword(@RequestBody @Validated final EmailRequest emailRequest) {
        authService.requestEmailUpdatePassword(emailRequest);
    }

    @PostMapping(ACTIVE_USER)
    @Operation(summary = "Active User, JWT Token")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void activeUser(@RequestHeader(AUTHORIZATION) final String jwt,
                           @RequestBody @Validated final EmailRequest emailRequest) {
        authService.activeUser(jwt, emailRequest);
    }

    @PostMapping(SEND_MESSAGE_EMAIL_NOT_ACTIVE)
    @Operation(summary = "Re-sending the account activation letter if the first letter was not successful")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void sendEmailSecondActive(@RequestBody @Validated final EmailRequest emailRequest) {
        authService.sendEmailActive(emailRequest);
    }

    @PostMapping(GOOGLE_LOGIN)
    @Operation(summary = "Google Login (Beta Version)")
    @ApiResponseOK
    public UserEntity googleLogin(@RequestBody final GoogleTokenRequest token) {
        return googleService.googleLogin(token);
    }
}