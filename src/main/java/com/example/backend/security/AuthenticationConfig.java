package com.example.backend.security;

import com.example.backend.security.service.details.MyUserDetailsService;
import com.example.backend.utils.general.MyPasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@AllArgsConstructor
public class AuthenticationConfig {

    private final MyPasswordEncoder myPasswordEncoder;
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(myPasswordEncoder.passwordEncoder());
        return authProvider;
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfiguration) {
        return authConfiguration.getAuthenticationManager();
    }
}