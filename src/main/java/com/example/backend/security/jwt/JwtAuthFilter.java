package com.example.backend.security.jwt;

import com.example.backend.security.service.JwtService;
import com.example.backend.security.service.details.MyUserDetails;
import com.example.backend.security.service.details.MyUserDetailsService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.backend.web.utils.Constants.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final MyUserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt, userEmail;

        if (authHeader != null && authHeader.startsWith(BEARER)) {

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserEmail(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
