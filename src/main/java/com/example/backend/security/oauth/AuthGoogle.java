package com.example.backend.security.oauth;

import com.example.backend.security.service.JwtService;
import com.example.backend.security.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import com.example.backend.web.User.utils.RegisterAuthStatus;
import com.example.backend.web.User.utils.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.example.backend.web.utils.Constants.DEPLOY;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthGoogle extends SimpleUrlAuthenticationSuccessHandler {

    private final MyPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if (RegisterAuthStatus.GOOGLE.name().toLowerCase()
                .equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            Map<String, Object> attributes = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();
            var email = attributes.getOrDefault("email", "").toString();

            userService.getByEmail(email)
                    .ifPresentOrElse(user -> SecurityContextHolder.getContext().setAuthentication(
                                    createOAuth2AuthenticationToken(
                                            createOAuth2User(user.getRole().name(), attributes), user.getRole().name(),
                                            oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
                            ), () -> {
                                var saveUser = createUserEntity(attributes, email);
                                userService.mySave(saveUser);
                                SecurityContextHolder.getContext().setAuthentication(
                                        createOAuth2AuthenticationToken(
                                                createOAuth2User(saveUser.getRole().name(), attributes),
                                                saveUser.getRole().name(),
                                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
                                );
                            }
                    );

            response.addHeader("Set-Cookie", "jwt="
                    + jwtService.generateAccessToken(authentication) + "; Path=/; HttpOnly; SameSite=None; Secure");
        }

        response.sendRedirect(DEPLOY);
    }

    private DefaultOAuth2User createOAuth2User(String roleName, Map<String, Object> attributes) {
        final String nameAttributeKey = "email";
        if (!attributes.containsKey(nameAttributeKey)) {
            throw new IllegalArgumentException("Missing '" + nameAttributeKey +
                    "' attribute in OAuth2 user attributes");
        }
        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(roleName)), attributes, nameAttributeKey);
    }

    private OAuth2AuthenticationToken createOAuth2AuthenticationToken(DefaultOAuth2User user, String roleName,
                                                                      String registrationId) {
        return new OAuth2AuthenticationToken(user, List.of(new SimpleGrantedAuthority(roleName)), registrationId);
    }

    private UserEntity createUserEntity(Map<String, Object> attributes, String email) {
        return UserEntity.builder()
                .email(email)
                .firstname(attributes.getOrDefault("given_name", "").toString())
                .lastname(attributes.getOrDefault("family_name", "").toString())
                .registerAuthStatus(RegisterAuthStatus.GOOGLE)
                .role(Role.USER)
                .password(passwordEncoder.passwordEncoder().encode(generateRandomPassword()))
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
