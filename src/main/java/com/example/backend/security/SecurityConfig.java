package com.example.backend.security;

import com.example.backend.security.jwt.JwtAuthFilter;
import com.example.backend.security.utils.CorsConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final CorsConfig corsConfig;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/info/**").authenticated()
                        .anyRequest()
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .oauth2Login(oauth -> oauth
                        .loginPage("/api/auth/signup")
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}