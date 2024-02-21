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
                                var newUser = createOAuth2User(user.getRole().name(), attributes);
                                var securityAuth = createOAuth2AuthenticationToken(newUser, user.getRole().name(), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                            }, () -> {
                                var saveUser = createUserEntity(attributes, email);
                                userRepository.save(saveUser);
                                var newUser = createOAuth2User(saveUser.getRole().name(), attributes);
                                var securityAuth = createOAuth2AuthenticationToken(newUser, saveUser.getRole().name(), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                            }
                    );
        }
    }

    private DefaultOAuth2User createOAuth2User(String roleName, Map<String, Object> attributes) {
        String nameAttributeKey = "email";
        if (!attributes.containsKey(nameAttributeKey)) {
            throw new IllegalArgumentException("Missing '" + nameAttributeKey + "' attribute in OAuth2 user attributes");
        }
        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(roleName)), attributes, nameAttributeKey);
    }

    private OAuth2AuthenticationToken createOAuth2AuthenticationToken(DefaultOAuth2User user, String roleName, String registrationId) {
        return new OAuth2AuthenticationToken(user, List.of(new SimpleGrantedAuthority(roleName)), registrationId);
    }

    private UserEntity createUserEntity(Map<String, Object> attributes, String email) {
        return UserEntity.builder()
                .email(email)
                .firstname(attributes.getOrDefault("given_name", "").toString())
                .lastname(attributes.getOrDefault("family_name", "").toString())
                .registerAuthStatus(RegisterAuthStatus.GOOGLE)
                .role(Role.USER)
                .password(passwordEncoder.encode(generateRandomPassword()))
                .phone(attributes.getOrDefault("phone", "").toString())
                .build();
    }

    private static String generateRandomPassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[15];
        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes);
    }

}