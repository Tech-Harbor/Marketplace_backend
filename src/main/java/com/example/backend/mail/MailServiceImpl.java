package com.example.backend.mail;

import com.example.backend.security.service.JwtTokenService;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import freemarker.template.Configuration;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;

import static com.example.backend.utils.general.Constants.JWT;
import static com.example.backend.utils.general.Constants.UTF_8;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private JwtTokenService jwtTokenService;
    private Configuration configuration;
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(final UserSecurityDTO user, final MailType type, final Properties params) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user);
            case NEW_PASSWORD -> sendNewPassword(user);
            case UPDATED_PASSWORD -> sendUpdatedPassword(user);
            default -> { }
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(final UserSecurityDTO user) {
        final var mimeMessage = mailSender.createMimeMessage();
        final var emailContent = getRegistrationEmailContent(user);
        final var helper = new MimeMessageHelper(mimeMessage, false, UTF_8);

        helper.setSubject("???, " + user.lastname());
        helper.setTo(user.email());
        helper.setText(emailContent, true);

        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final UserSecurityDTO user) {
        final var writer = new StringWriter();
        final var model = new HashMap<String, Object>();

        model.put("username", user.lastname());
        model.put(JWT, jwtTokenService.generateUserEmailDataToken(user));

        configuration.getTemplate("register.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private void sendNewPassword(final UserSecurityDTO user) {
        final var mimePasswordMessage = mailSender.createMimeMessage();
        final var passwordContent = getNewPasswordContent(user);
        final var helper = new MimeMessageHelper(mimePasswordMessage, false, UTF_8);

        helper.setSubject("Update Password, " + user.lastname());
        helper.setTo(user.email());
        helper.setText(passwordContent, true);

        mailSender.send(mimePasswordMessage);
    }

    @SneakyThrows
    private String getNewPasswordContent(final UserSecurityDTO user) {
        final var writer = new StringWriter();
        final var model = new HashMap<String, Object>();

        model.put("username", user.lastname());
        model.put(JWT, jwtTokenService.generateUserPasswordDataToken(user));

        configuration.getTemplate("newPassword.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private void sendUpdatedPassword(final UserSecurityDTO user) {
        final var mimeUpdatedPasswordMessage = mailSender.createMimeMessage();
        final var passwordContent = getUpdatedPasswordContent(user);
        final var helper = new MimeMessageHelper(mimeUpdatedPasswordMessage, false, UTF_8);

        helper.setSubject("Updated Password, " + user.lastname());
        helper.setTo(user.email());
        helper.setText(passwordContent, true);

        mailSender.send(mimeUpdatedPasswordMessage);
    }

    @SneakyThrows
    private String getUpdatedPasswordContent(final UserSecurityDTO user) {
        final var writer = new StringWriter();
        final var model = new HashMap<String, Object>();

        model.put("username", user.lastname());

        configuration.getTemplate("updatedPassword.ftlh").process(model, writer);

        return writer.getBuffer().toString();
    }
}