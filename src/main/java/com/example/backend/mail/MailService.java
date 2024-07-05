package com.example.backend.mail;

import com.example.backend.web.User.store.dto.UserSecurityDTO;

import java.util.Properties;

public interface MailService {
    void sendEmail(UserSecurityDTO user, MailType type, Properties params);
}