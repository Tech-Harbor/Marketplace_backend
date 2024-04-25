package com.example.backend.security.jwt;

import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.details.MyUserDetails;
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

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) {

        final var authHeader = request.getHeader(AUTHORIZATION);

        if (StringUtils.isNoneEmpty(authHeader) && authHeader.startsWith(BEARER)) {

            var jwt = authHeader.substring(7);

            var userData = jwtService.extractUserData(jwt);

            if (StringUtils.isNoneEmpty(userData) && SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(userData);

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    final var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    response.addHeader(AUTHORIZATION, BEARER + jwt);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}