package com.example.backend.security.controller;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.servers.AuthServer;
import com.example.backend.utils.annotations.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.backend.utils.general.Constants.BEARER_AUTHENTICATION;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
@Tag(name = "Authentication")
public class AuthController {

    private final AuthServer authServer;

    private static final String SIGNUP_URI = "/auth/signup";
    private static final String LOGIN_URI = "/auth/login";
    private static final String UPDATE_JWT_URI = "/refresh-token";
    private static final String FORM_CHANGE_PASSWORD_URI = "/change-password";
    private static final String REQUEST_EMAIL_UPDATE_PASSWORD = "/request/email";
    private static final String ACTIVE_USER = "/active";
    private static final String SEND_MESSAGE_EMAIL_NOT_ACTIVE = "/sendMessageEmailActive";

    @PostMapping(SIGNUP_URI)
    @SecurityRequirement(name = BEARER_AUTHENTICATION)
    @Operation(summary = "Register user")
    @ApiResponseCreated
    @ApiResponseBadRequest
    public void signup(@RequestBody @Validated final RegisterRequest registerRequest) {
        authServer.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    @SecurityRequirement(name = BEARER_AUTHENTICATION)
    @Operation(summary = "Login user")
    @ApiResponseOK
    @ApiResponseUnauthorized
    @ApiResponseForbidden
    public AuthResponse login(@RequestBody @Validated final AuthRequest authRequest) {
        return authServer.login(authRequest);
    }

    @PostMapping(UPDATE_JWT_URI)
    @Operation(summary = "Update refreshToken user")
    @ApiResponseTokenOK
    public void refreshToken(final HttpServletRequest request, final HttpServletResponse response) {
        authServer.updateRefreshToken(request, response);
    }

    @PutMapping(FORM_CHANGE_PASSWORD_URI)
    @Operation(summary = "Update Password User")
    @ApiResponseOK
    @ApiResponseNotFound
    @ApiResponseBadRequest
    public void updatePassword(@RequestHeader(AUTHORIZATION) final String jwt,
                               @RequestBody @Validated final PasswordRequest passwordRequest) {
        authServer.formUpdatePassword(jwt, passwordRequest);
    }

    @PostMapping(REQUEST_EMAIL_UPDATE_PASSWORD)
    @Operation(summary = "Change password using email")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void requestEmailUpdatePassword(@RequestBody @Validated final EmailRequest emailRequest) {
        authServer.requestEmailUpdatePassword(emailRequest);
    }

    @PostMapping(ACTIVE_USER)
    @Operation(summary = "Active User, JWT Token")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void activeUser(@RequestHeader(AUTHORIZATION) final String jwt) {
        authServer.activeUser(jwt);
    }

    @PostMapping(SEND_MESSAGE_EMAIL_NOT_ACTIVE)
    @Operation(summary = "Re-sending the account activation letter if the first letter was not successful")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    public void sendEmailSecondActive(@RequestBody @Validated final EmailRequest emailRequest) {
        authServer.sendEmailActive(emailRequest);
    }
}