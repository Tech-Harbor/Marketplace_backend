package com.example.backend.security.service;

import com.example.backend.security.models.request.AuthRequest;
import com.example.backend.security.models.request.EmailRequest;
import com.example.backend.security.models.request.PasswordRequest;
import com.example.backend.security.models.request.RegisterRequest;
import com.example.backend.security.models.response.AuthResponse;

public interface AuthService {
    /**
     * Method for user registration in the system.
     *
     * @param registerRequest The object containing the data for user registration
     * @throws RuntimeException Custom exception thrown if the provided data is invalid or if a
     *          user with the same email already exists
     * @Returns void
     */
    void signup(RegisterRequest registerRequest);
    /**
     * Method for user login authentication and generating access and refresh tokens.
     *
     * @param authRequest The object containing the user's email and password for authentication
     * @throws RuntimeException if user with the provided email is not found
     * @Returns AuthResponse containing the generated access and refresh tokens
     */
    AuthResponse login(AuthRequest authRequest);
    /**
     * Method for updating user password.
     *
     * @param jwt The JWT token used for authentication and authorization
     * @param passwordRequest The object containing the new password
     * @throws RuntimeException if user with the provided ID is not found
     * @Returns void
     */
    void formUpdatePassword(String jwt, PasswordRequest passwordRequest);
    /**
     * Method for requesting email update for password reset.
     *
     *
     * @param emailRequest The object containing the email for which password reset is requested
     * @throws RuntimeException if the email does not exist in the system
     * @Returns void
     */
    void requestEmailUpdatePassword(EmailRequest emailRequest);
    /**
     * Method for activating a user in the system.
     *
     * @param jwt The JWT token used for authentication and authorization
     * @throws RuntimeException if user with the provided ID is not found
     * @Returns void
     */
    void activeUser(String jwt);
    /**
     * This method sends a letter to the user's mail if he did not have time to activate the account the first time
     */
    void sendEmailActive(EmailRequest emailRequest);
}