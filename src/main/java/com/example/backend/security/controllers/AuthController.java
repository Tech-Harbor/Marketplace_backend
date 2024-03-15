package com.example.backend.security.controllers;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication User and Update Password, personal office users")
public class AuthController {

    private AuthService authService;

    private static final String SIGNUP_URI = "/signup";
    private static final String LOGIN_URI = "/login";
    private static final String FORM_UPDATE_PASSWORD_URI = "/update/password/{userId}";
    private static final String REQUEST_EMAIL_UPDATE_PASSWORD = "/request/email";
    private static final String INFO = "/info";

    @PostMapping(SIGNUP_URI)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
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
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
        }
    )
    public AuthResponse login(@RequestBody @Validated final AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PutMapping(FORM_UPDATE_PASSWORD_URI)
    @Operation(summary = "Update Password User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        }
    )
    public void updatePassword(@PathVariable final Long userId,
                               @RequestBody @Validated final PasswordRequest passwordRequest) {
        authService.formUpdatePassword(userId, passwordRequest);
    }

    @GetMapping(INFO)
    @Operation(summary = "Information about the user who is authorized and logged into the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        }
    )
    public String info(@AuthenticationPrincipal final UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @PostMapping(REQUEST_EMAIL_UPDATE_PASSWORD)
    @Operation(summary = "Change password using email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        }
    )
    public void requestEmailUpdatePassword(@RequestBody @Validated final EmailRequest emailRequest) {
        authService.requestEmailUpdatePassword(emailRequest);
    }
}