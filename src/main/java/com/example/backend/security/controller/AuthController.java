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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @SecurityRequirement(name = BEARER_AUTHENTICATION)
    @Operation(summary = "Register user")
    @ApiResponseCreated
    @ApiResponseBadRequest
    @PostMapping(value = SIGNUP_URI, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void signup(@RequestBody @Validated final RegisterRequest registerRequest) {
        authServer.signup(registerRequest);
    }

    @SecurityRequirement(name = BEARER_AUTHENTICATION)
    @Operation(summary = "Login user")
    @ApiResponseOK
    @ApiResponseUnauthorized
    @ApiResponseForbidden
    @PostMapping(value = LOGIN_URI, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AuthResponse login(@RequestBody @Validated final AuthRequest authRequest) {
        return authServer.login(authRequest);
    }

    @Operation(summary = "Update refreshToken user")
    @ApiResponseTokenOK
    @PostMapping(value = UPDATE_JWT_URI, produces = APPLICATION_JSON_VALUE)
    public void refreshToken(final HttpServletRequest request, final HttpServletResponse response) {
        authServer.updateRefreshToken(request, response);
    }

    @Operation(summary = "Update Password User")
    @ApiResponseOK
    @ApiResponseNotFound
    @ApiResponseBadRequest
    @PutMapping(value = FORM_CHANGE_PASSWORD_URI, produces = APPLICATION_JSON_VALUE)
    public void updatePassword(@RequestHeader(AUTHORIZATION) final String jwt,
                               @RequestBody @Validated final PasswordRequest passwordRequest) {
        authServer.formUpdatePassword(jwt, passwordRequest);
    }

    @Operation(summary = "Change password using email")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    @PostMapping(value = REQUEST_EMAIL_UPDATE_PASSWORD, consumes = APPLICATION_JSON_VALUE)
    public void requestEmailUpdatePassword(@RequestBody @Validated final EmailRequest emailRequest) {
        authServer.requestEmailUpdatePassword(emailRequest);
    }

    @Operation(summary = "Active User, JWT Token")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    @PostMapping(value = ACTIVE_USER, consumes = APPLICATION_JSON_VALUE)
    public void activeUser(@RequestHeader(AUTHORIZATION) final String jwt) {
        authServer.activeUser(jwt);
    }

    @Operation(summary = "Re-sending the account activation letter if the first letter was not successful")
    @ApiResponseEmailOK
    @ApiResponseBadRequest
    @PostMapping(value = SEND_MESSAGE_EMAIL_NOT_ACTIVE, consumes = APPLICATION_JSON_VALUE)
    public void sendEmailSecondActive(@RequestBody @Validated final EmailRequest emailRequest) {
        authServer.sendEmailActive(emailRequest);
    }
}