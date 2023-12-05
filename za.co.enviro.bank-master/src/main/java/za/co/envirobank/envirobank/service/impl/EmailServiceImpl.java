package za.co.envirobank.envirobank.service.impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import za.co.envirobank.envirobank.model.entities.PasswordToken;
import za.co.envirobank.envirobank.service.EmailService;


import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    @Override
    @Async
    public void sendSignupEmail(String to, String password) {
        //, String email
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", to, "url", password));
            String text = templateEngine.process("signupemailtemplate", context);

            MimeMessage mimeMessage = getMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setPriority(1);
            helper.setSubject("Login Details");
            helper.setFrom("envirobank@gmail.com");
            helper.setTo(to);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            try {
                throw new IllegalAccessException("Failed to email");
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void sendResetEmailPassword(PasswordToken tokenService) {

        try {
            Context context = new Context();
            context.setVariables(Map.of("name", tokenService.getUser().getName(),
                    "url", "http://localhost:4200/change-password?token=" + tokenService.getToken()));
            String text = templateEngine.process("resetemailemailtemplate", context);

            MimeMessage mimeMessage = getMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setPriority(1);
            helper.setSubject("Reset password");
            helper.setFrom("envirobank@gmail.com");
            helper.setTo(tokenService.getUser().getEmail());
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            try {
                throw new IllegalAccessException("Failed to email");
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }


}