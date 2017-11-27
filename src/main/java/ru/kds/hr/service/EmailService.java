package ru.kds.hr.service;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Email service
 */
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private String senderEmail;

    private String senderName;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, @Value("${application.mail.senderEmail}") String senderEmail,
            @Value("${application.mail.senderName}") String senderName) {
        this.javaMailSender = javaMailSender;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
    }

    public void postMail(String recipientEmail, String subject, String body, Object mailModel)
            throws MessagingException, UnsupportedEncodingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setTo(recipientEmail);
        message.setFrom(senderEmail, senderName);
        message.setSubject(subject);

        JtwigTemplate template = JtwigTemplate.inlineTemplate(body);
        JtwigModel model = JtwigModel.newModel().with("model", mailModel);

        String text = template.render(model);
        message.setText(text, true);

        javaMailSender.send(mimeMessage);
    }
}
