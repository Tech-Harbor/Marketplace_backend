package com.example.backend.security.service.impl;

import com.cloudinary.utils.StringUtils;
import com.example.backend.security.models.request.GoogleTokenRequest;
import com.example.backend.security.service.GoogleService;
import com.example.backend.utils.general.MyPasswordEncoder;
import com.example.backend.utils.props.GoogleProperties;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.example.backend.utils.enums.RegisterAuthStatus.GOOGLE;
import static com.example.backend.utils.enums.Role.USER;
import static com.example.backend.utils.general.MyPasswordEncoder.generateRandomPassword;
import static com.example.backend.web.exception.RequestException.badRequestException;

@Service
@Slf4j
@AllArgsConstructor
public class GoogleServiceImpl implements GoogleService {

    private final MyPasswordEncoder myPasswordEncoder;
    private final GoogleProperties googleProperties;
    private final UserService userService;

    @Override
    @SneakyThrows
    public UserEntity googleLogin(final GoogleTokenRequest googleTokenRequest) {
        var transport = new NetHttpTransport();

        var jsonFactory = GsonFactory.getDefaultInstance();

        var googleIdTokenVerifier = new GoogleIdTokenVerifier
                .Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleProperties.getClientId()))
                .build();

        var idToken = googleIdTokenVerifier.verify(googleTokenRequest.token());

        if (StringUtils.isNotBlank(idToken)) {
            var payload = idToken.getPayload();

            String email = payload.getEmail();
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            return getCreateUserGoogle(email, familyName, givenName);
        } else {
            throw badRequestException("Invalid ID token.");
        }
    }

    private UserEntity getCreateUserGoogle(final String email, final String familyName, final String givenName) {
        var userEmailOptional = userService.getByEmail(email);

        var user = new UserEntity();

        if (userEmailOptional.isEmpty()) {
            user = UserEntity.builder()
                    .email(email)
                    .firstname(familyName)
                    .lastname(givenName)
                    .enabled(true)
                    .role(USER)
                    .registerAuthStatus(GOOGLE)
                    .password(myPasswordEncoder.passwordEncoder().encode(generateRandomPassword()))
                    .build();

            userService.mySave(user);
        }

        return user;
    }
}