package com.example.backend.mail;

import com.example.backend.web.User.UserEntity;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(UserEntity user, MailType type, Properties params) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user, params);
            case NEW_PASSWORD -> sendNewPassword(user, params);
            default -> {}
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(final UserEntity user, final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String emailContent = getRegistrationEmailContent(user, params);

        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage, false, "UTF-8"
        );

        helper.setSubject("Thank you for registration, " + user.getLastname());
        helper.setTo(user.getEmail());
        helper.setText(emailContent, true);

        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final UserEntity user, final Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("name", user.getLastname());

        configuration.getTemplate("register.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private void sendNewPassword(final UserEntity user, final Properties params) {
        MimeMessage mimePasswordMessage = mailSender.createMimeMessage();
        String passwordContent = getNewPasswordContent(user, params);

        MimeMessageHelper helper = new MimeMessageHelper(
                mimePasswordMessage, false, "UTF-8"
        );

        helper.setSubject("New Password" + user.getLastname());
        helper.setTo(user.getEmail());
        helper.setText(passwordContent, true);

        mailSender.send(mimePasswordMessage);
    }

    @SneakyThrows
    private String getNewPasswordContent(final UserEntity user, final Properties properties) {
        StringWriter writer = new StringWriter();

        Map<String, Object> model = new HashMap<>();

        model.put("name", user.getLastname());

        configuration.getTemplate("newPassword.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }
}