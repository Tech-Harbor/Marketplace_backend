package com.example.backend.mail;

import com.example.backend.web.User.UserEntity;

import java.util.Properties;

public interface MailService {
    void sendEmail(UserEntity user, MailType type, Properties params);
}
