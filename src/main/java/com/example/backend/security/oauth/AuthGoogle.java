package com.example.backend.security.oauth;

import com.example.backend.security.service.JwtService;
import com.example.backend.utils.MyPasswordEncoder;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import static com.example.backend.utils.Constants.EMAIL_KEY;
import static com.example.backend.utils.Constants.EMPTY_LINE;
import static com.example.backend.utils.Constants.DEPLOY_STORE;
import static com.example.backend.utils.Constants.COOK;
import static com.example.backend.utils.enums.RegisterAuthStatus.GOOGLE;
import static com.example.backend.utils.enums.Role.USER;

@Component
@RequiredArgsConstructor
public class AuthGoogle extends SimpleUrlAuthenticationSuccessHandler {

    private final MyPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) {

        final var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if (GOOGLE.name().toLowerCase().equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {

            final var attributes = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();

            final var email = attributes.getOrDefault(EMAIL_KEY, EMPTY_LINE).toString();

            userService.getByEmail(email)
                    .ifPresentOrElse(user -> SecurityContextHolder.getContext().setAuthentication(
                                createOAuth2AuthenticationToken(
                                        createOAuth2User(user.getRole().name(), attributes), user.getRole().name(),
                                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()
                                )
                            ), () -> {
                                final var saveUser = createUserEntity(attributes, email);

                                userService.mySave(saveUser);

                                SecurityContextHolder.getContext().setAuthentication(
                                        createOAuth2AuthenticationToken(
                                                createOAuth2User(saveUser.getRole().name(), attributes),
                                                saveUser.getRole().name(),
                                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()
                                        )
                                );
                            }
                    );
            response.addHeader("Set-Cookie", "jwt=" + jwtService.generateAccessToken(authentication) + COOK);
        }
        response.sendRedirect(DEPLOY_STORE);
    }

    private DefaultOAuth2User createOAuth2User(final String roleName, final Map<String, Object> attributes) {
        if (!attributes.containsKey(EMAIL_KEY)) {
            throw new IllegalArgumentException("Missing" + EMAIL_KEY + "attribute in OAuth2 user attributes");
        }

        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(roleName)), attributes, EMAIL_KEY);
    }

    private OAuth2AuthenticationToken createOAuth2AuthenticationToken(final DefaultOAuth2User user,
                                                                      final String roleName,
                                                                      final String registrationId) {
        return new OAuth2AuthenticationToken(user, List.of(new SimpleGrantedAuthority(roleName)), registrationId);
    }

    private UserEntity createUserEntity(final Map<String, Object> attributes, final String email) {
        return UserEntity.builder()
                .email(email)
                .firstname(attributes.getOrDefault("given_name", EMPTY_LINE).toString())
                .lastname(attributes.getOrDefault("family_name", EMPTY_LINE).toString())
                .registerAuthStatus(GOOGLE)
                .role(USER)
                .password(passwordEncoder.passwordEncoder().encode(generateRandomPassword()))
                .phone(attributes.getOrDefault("phone", EMPTY_LINE).toString())
                .build();
    }

    private static String generateRandomPassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[15];

        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes);
    }
}