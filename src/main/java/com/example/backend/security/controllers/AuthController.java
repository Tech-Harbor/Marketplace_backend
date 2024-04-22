package com.example.backend.security.controllers;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.models.response.ErrorResponse;
import com.example.backend.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "Authentication", description = "Authentication User and Update Password, personal office users")
public class AuthController {

    private final AuthService authService;

    private static final String SIGNUP_URI = "/auth/signup";
    private static final String LOGIN_URI = "/auth/login";
    private static final String FORM_CHANGE_PASSWORD_URI = "/change-password";
    private static final String REQUEST_EMAIL_UPDATE_PASSWORD = "/request/email";
    private static final String ACTIVE_USER = "/active";
    private static final String INFO = "/accouth";
    private static final String SEND_MESSAGE_EMAIL_NOT_ACTIVE = "/sendMessageEmail";

    @PostMapping(SIGNUP_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "This email has already been used")
        }
    )
    public void signup(@RequestBody @Validated final RegisterRequest registerRequest) {
        authService.signup(registerRequest);
    }

    @PostMapping(LOGIN_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Ok",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = AuthResponse.class))
                    }
                ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ErrorResponse.class))
                    }
                )
        }
    )
    public AuthResponse login(@RequestBody @Validated final AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PutMapping(FORM_CHANGE_PASSWORD_URI)
    @Operation(summary = "Update Password User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Ok",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = PasswordRequest.class))
                    }
            ),
        }
    )
    public void updatePassword(@RequestHeader(AUTHORIZATION) final String jwt,
                               @RequestBody @Validated final PasswordRequest passwordRequest) {
        authService.formUpdatePassword(jwt, passwordRequest);
    }

    @GetMapping(INFO)
    @Operation(summary = "Information about the user who is authorized and logged into the system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Ok",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = String.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ErrorResponse.class))
                        }
                )
        }
    )
    public String info(@AuthenticationPrincipal final UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @PostMapping(REQUEST_EMAIL_UPDATE_PASSWORD)
    @Operation(summary = "Change password using email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = EmailRequest.class))
                    }
            ),
            @ApiResponse(responseCode = "500", description = "This email is not exists")
        }
    )
    public void requestEmailUpdatePassword(@RequestBody @Validated final EmailRequest emailRequest) {
        authService.requestEmailUpdatePassword(emailRequest);
    }

    @PostMapping(ACTIVE_USER)
    @Operation(summary = "Active User, JWT Token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content =
                        @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                        @Schema(implementation = AuthRequest.class)
                    )
            ),
        }
    )
    public void activeUser(@RequestHeader(AUTHORIZATION) final String jwt) {
        authService.activeUser(jwt);
    }

    @PostMapping(SEND_MESSAGE_EMAIL_NOT_ACTIVE)
    @Operation(summary = "Re-sending the account activation letter if the first letter was not successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public void sendEmailSecondActive() {
        authService.sendEmailActive();
    }
}