package com.example.backend.security.service;

import com.example.backend.web.User.UserEntity;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    /**
     * Generates an access token for the provided authentication.
     *
     * @param authentication The authentication object containing user details
     * @return The generated access token
     */
    String generateAccessToken(Authentication authentication);
    /**
     * Generates an refresh token for the provided authentication.
     *
     * @param authentication The authentication object containing user details
     * @return The generated refresh token
     */
    String generateRefreshToken(Authentication authentication);
    /**
     * Generates a new password token and activates the user associated with the provided userData.
     *
     * @param email The email of the user to generate the token for and activate
     * @return The generated token
     */
    String generateUserPasswordDataToken(UserEntity userData);
    String generateUserEmailDataToken(UserEntity userData);
}