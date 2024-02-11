package com.example.backend.security;

import com.example.backend.security.utils.CorsConfig;
import com.example.backend.web.User.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthGoogle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CorsConfig corsConfig;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain,
            @NotNull Authentication authentication) {

    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

    if(RegisterAuthStatus.GOOGLE.name().equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){

        var principal = (DefaultOAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = principal.getAttributes();

        var email = attributes.getOrDefault("email", "").toString();

        var lastname = attributes.getOrDefault("lastname", "").toString();

        userService.getByEmail(email)
            .ifPresentOrElse(user -> {

                var newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(
                        user.getRole().name())), attributes, "id");

                var securityAuth = new OAuth2AuthenticationToken(newUser,List.of(new SimpleGrantedAuthority(
                        user.getRole().name())), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                SecurityContextHolder.getContext().setAuthentication(securityAuth);

            }, () -> {

                var saveUser = UserEntity.builder()
                        .email(email)
                        .lastname(lastname)
                        .registerAuthStatus(RegisterAuthStatus.GOOGLE)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(saveUser);

                var newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(
                        saveUser.getRole().name())), attributes, "id");

                var securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(
                        saveUser.getRole().name())), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                }
            );
    }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(corsConfig.corsConfigurationSource().toString());
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}