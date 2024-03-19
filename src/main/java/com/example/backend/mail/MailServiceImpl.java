package com.example.backend.mail;

import com.example.backend.security.service.JwtService;
import com.example.backend.web.User.UserEntity;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private Configuration configuration;
    private JavaMailSender mailSender;
    private JwtService jwtService;

    @Override
    public void sendEmail(final UserEntity user, final MailType type, final Properties params) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user);
            case NEW_PASSWORD -> sendNewPassword(user);
            default -> { }
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(final UserEntity user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String emailContent = getRegistrationEmailContent(user);

        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage, false, "UTF-8"
        );

        helper.setSubject("Thank you for registration, " + user.getLastname());
        helper.setTo(user.getEmail());
        helper.setText(emailContent, true);

        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final UserEntity user) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("username", user.getLastname());

        configuration.getTemplate("register.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private void sendNewPassword(final UserEntity user) {
        MimeMessage mimePasswordMessage = mailSender.createMimeMessage();
        String passwordContent = getNewPasswordContent(user);

        MimeMessageHelper helper = new MimeMessageHelper(
                mimePasswordMessage, false, "UTF-8"
        );

        helper.setSubject("Account activation, " + user.getLastname());
        helper.setTo(user.getEmail());
        helper.setText(passwordContent, true);

        mailSender.send(mimePasswordMessage);
    }

    @SneakyThrows
    private String getNewPasswordContent(final UserEntity user) {
        StringWriter writer = new StringWriter();

        Map<String, Object> model = new HashMap<>();

        model.put("username", user.getLastname());
        model.put("newPasswordToken", jwtService.generateNewPasswordToken(user.getEmail()));

        configuration.getTemplate("newPassword.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }
}