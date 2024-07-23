package com.example.backend.security.servers;

import com.example.backend.security.servers.details.MyUserDetails;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtServer {
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
     * Checks if the provided token is valid for the given user details.
     *
     * @param token The token to validate
     * @param userDetails The user details associated with the token
     * @return True if the token is valid for the user details, false otherwise
     */
    boolean isTokenValid(String token,  MyUserDetails userDetails);
}