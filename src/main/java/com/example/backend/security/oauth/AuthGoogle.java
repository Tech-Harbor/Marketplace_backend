package com.example.backend.security.oauth;

import com.example.backend.security.utils.CorsConfig;
import com.example.backend.web.User.*;
import com.example.backend.web.User.utils.RegisterAuthStatus;
import com.example.backend.web.User.utils.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthGoogle extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CorsConfig corsConfig;
    private final PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Authentication authentication) {

    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if (RegisterAuthStatus.GOOGLE.name().toLowerCase()
                .equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {

            var principal = (DefaultOAuth2User) authentication.getPrincipal();

            Map<String, Object> attributes = principal.getAttributes();

            var email = attributes.getOrDefault("email", "").toString();

            userService.getByEmail(email)
                    .ifPresentOrElse(user -> {
                                var newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(
                                        user.getRole().name())), attributes, "id");
                                var securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(
                                        user.getRole().name())), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                            }, () -> {

                                var saveUser = UserEntity.builder()
                                        .email(email)
                                        .firstname(attributes.getOrDefault("firstname", "").toString())
                                        .lastname(attributes.getOrDefault("lastname", "").toString())
                                        .registerAuthStatus(RegisterAuthStatus.GOOGLE)
                                        .role(Role.USER)
                                        .password(passwordEncoder.encode(generateRandomPassword()))
                                        .phone(attributes.getOrDefault("phone", "").toString())
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
        //        this.setAlwaysUseDefaultTargetUrl(true);
        //        this.setDefaultTargetUrl(corsConfig.corsConfigurationSource().toString());
        //        super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    private static String generateRandomPassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[15];
        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes);
    }

}