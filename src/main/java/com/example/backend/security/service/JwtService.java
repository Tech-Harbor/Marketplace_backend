package com.example.backend.security.service;

import com.example.backend.security.service.details.MyUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.function.Function;

public interface JwtService {
    /**
     * Extracts the userData from the provided JWT token.
     *
     * @param token The JWT token from which the user email needs to be extracted
     * @return The user email extracted from the JWT token
     */
    String extractUserData(String token);
    /**
     * Extracts a specific claim from the provided JWT token.
     *
     * @param token The JWT token from which the claim needs to be extracted
     * @param claimsResolver The function to resolve the specific claim from the token's claims
     * @param <T> The type of the claim to be extracted
     * @return The extracted claim of type T
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
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
    String generateUserDataToken(String userData);
    /**
     * Checks if the provided token is valid for the given user details.
     *
     * @param token The token to validate
     * @param userDetails The user details associated with the token
     * @return True if the token is valid for the user details, false otherwise
     */
    boolean isTokenValid(String token,  MyUserDetails userDetails);
}