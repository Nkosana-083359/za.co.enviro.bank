package za.co.envirobank.envirobank.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "text/email-text";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JavaMailSender mailSender;

    @Qualifier("emailTemplateEngine")
    @Autowired
    private TemplateEngine textTemplateEngine;


    public void sendTextMail(
            final String recipientName,String password,String username,final String recipientEmail, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("credentials", Arrays.asList(password, username));


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example plain TEXT email");
        message.setFrom("enviroBank@example.com");
        message.setTo(recipientEmail);


        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);
        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }




}