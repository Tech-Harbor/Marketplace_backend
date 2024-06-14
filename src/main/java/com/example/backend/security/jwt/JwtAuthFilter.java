package com.example.backend.security.jwt;

import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.details.MyUserDetailsService;
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
public class JwtAuthFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) {

        final var jwt = getTokenHeaders(request);

        final var userData = getExtractUserData(jwt);

        getSecurityContextHolder(request, userData, jwt);

        filterChain.doFilter(request, response);
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
            return jwtService.extractUserData(jwt);
        }

        return null;
    }

    private void getSecurityContextHolder(final HttpServletRequest request, final String userData, final String jwt) {
        if (StringUtils.isNoneEmpty(userData) && isNotAuthenticated()) {

            final var userDetails = userDetailsService.loadUserByUsername(userData);

            if (jwtService.isTokenValid(jwt, userDetails)) {

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

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}