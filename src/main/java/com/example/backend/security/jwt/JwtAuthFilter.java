package com.example.backend.security.jwt;

import com.example.backend.security.models.response.AuthResponse;
import com.example.backend.security.servers.JwtServer;
import com.example.backend.security.servers.JwtTokenServer;
import com.example.backend.security.servers.details.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.backend.utils.general.Constants.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter implements JwtAuthServerFilter{

    private final MyUserDetailsService userDetailsService;
    private final JwtTokenServer jwtTokenServer;
    private final JwtServer jwtServer;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            final @Nullable HttpServletRequest request,
            final @Nullable HttpServletResponse response,
            final FilterChain filterChain) {

        assert request != null;

        final var jwt = getTokenHeaders(request);

        final var userData = getExtractUserData(jwt);

        getSecurityContextHolder(request, userData, jwt);

        filterChain.doFilter(request, response);
    }

    @Override
    public void updateRefreshTokenFilter(final HttpServletRequest request, final HttpServletResponse response) {
        updateRefreshTokenFilterServer(request, response);
    }

    private String getTokenHeaders(final HttpServletRequest request) {
        final var authHeader = request.getHeader(AUTHORIZATION);

        if (StringUtils.isNoneEmpty(authHeader) && authHeader.startsWith(BEARER)) {
            return authHeader.substring(7);
        }

        return null;
    }

    private String getExtractUserData(final String jwt) {

        if (StringUtils.isNoneEmpty(jwt)) {
            return jwtServer.extractUserData(jwt);
        }

        return null;
    }

    private void getSecurityContextHolder(final HttpServletRequest request, final String userData, final String jwt) {
        if (StringUtils.isNoneEmpty(userData) && isNotAuthenticated()) {

            final var userDetails = userDetailsService.loadUserByUsername(userData);

            if (jwtServer.isTokenValid(jwt, userDetails)) {

                final var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
    }

    @SneakyThrows
    private void updateRefreshTokenFilterServer(final HttpServletRequest request, final HttpServletResponse response) {
        final var refreshToken = getTokenHeaders(request);

        final var userData = getExtractUserData(refreshToken);

        if (StringUtils.isNoneEmpty(userData)) {

            final var userDetails = userDetailsService.loadUserByUsername(userData);

            if (jwtServer.isTokenValid(refreshToken, userDetails)) {
                final var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                final var accessTokenNew = jwtTokenServer.generateAccessToken(authenticationToken);
                final var refreshTokenNew = jwtTokenServer.generateRefreshToken(authenticationToken);

                final var authUpdateJwtResponse = AuthResponse.builder()
                        .accessToken(accessTokenNew)
                        .refreshToken(refreshTokenNew)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authUpdateJwtResponse);
            }
        }
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}