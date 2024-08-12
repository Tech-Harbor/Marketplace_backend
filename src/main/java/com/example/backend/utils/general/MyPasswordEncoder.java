package com.example.backend.utils.general;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MyPasswordEncoder implements PasswordEncoder{
    @Override
    public String encode(CharSequence rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

    public static String generateRandomPassword() {
        return Base64.getEncoder().encodeToString(RandomStringUtils.randomAlphanumeric(15).getBytes());
    }
}