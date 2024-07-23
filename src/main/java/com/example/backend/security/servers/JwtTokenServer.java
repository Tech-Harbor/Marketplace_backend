package com.example.backend.security.servers;

import com.example.backend.web.User.store.dto.UserSecurityDTO;
import org.springframework.security.core.Authentication;

public interface JwtTokenServer {
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
     * Generates a JWT token with the user's password included in the claims.
     * The token includes information such as the password and email of the user,
     * along with issued-at and expiration times.
     *
     * @param userData The UserSecurityDTO object representing the user for whom the token is being generated.
     * @return The generated JWT token as a String.
     */
    String generateUserPasswordDataToken(UserSecurityDTO userData);
    /**
     * Generates a JWT token with the user's email as the subject.
     * The token includes details about when it was issued and when it will expire.
     * This JWT can be used for authentication or verification purposes.
     *
     * @param userData The UserSecurityDTO object representing the user whose email will be used as the subject.
     * @return The generated JWT token as a String.
     */
    String generateUserEmailDataToken(UserSecurityDTO userData);
}