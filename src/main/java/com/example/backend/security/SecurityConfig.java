package com.example.backend.security;

import com.example.backend.security.exception.AccessDeniedHandlerJwt;
import com.example.backend.security.exception.AuthenticationEntryPointJwt;
import com.example.backend.security.jwt.JwtAuthFilter;
import com.example.backend.security.oauth.AuthGoogle;
import com.example.backend.utils.general.CorsConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.backend.utils.general.Constants.GET_AUTH;
import static com.example.backend.utils.general.Constants.PERMIT_ALL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;
    private final AuthenticationProvider authProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthGoogle authGoogle;
    private final CorsConfig corsConfig;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(corsConfig.corsConfigurationSource())
                )
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, GET_AUTH).authenticated()
                        .requestMatchers(PERMIT_ALL).permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandlerJwt)
                        .authenticationEntryPoint(authenticationEntryPointJwt)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .oauth2Login(oauth -> oauth
                        .successHandler(authGoogle)
                )
                .build();
    }
}