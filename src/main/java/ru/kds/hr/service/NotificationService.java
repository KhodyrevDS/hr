package ru.kds.hr.service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.kds.hr.domain.Employee;
import ru.kds.hr.domain.LetterTemplate;
import ru.kds.hr.domain.LetterTemplateRepository;

/**
 * Notification service
 */
@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private EmailService emailService;

    private LetterTemplateRepository letterTemplateRepository;

    public NotificationService(EmailService emailService, LetterTemplateRepository letterTemplateRepository) {
        this.emailService = emailService;
        this.letterTemplateRepository = letterTemplateRepository;
    }

    public void employeeHiredNotification(Employee employee) {
        try {
            LetterTemplate template = letterTemplateRepository.findByName(LetterTemplate.EMPLOYEE_HIRED);
            if (template == null) {
                LOGGER.warn("Letter template with name '" + LetterTemplate.EMPLOYEE_HIRED + "' does not exist");
            } else {
                emailService.postMail(employee.getEmail(), template.getSubject(), template.getBody(),
                        Collections.singletonMap("employee", employee));
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("Email notification error", e);
        }
    }

    public void employeeFiredNotification(Employee employee) {
        try {
            LetterTemplate template = letterTemplateRepository.findByName(LetterTemplate.EMPLOYEE_FIRED);
            if (template == null) {
                LOGGER.warn("Letter template with name '" + LetterTemplate.EMPLOYEE_FIRED + "' does not exist");
            } else {
                emailService.postMail(employee.getEmail(), template.getSubject(), template.getBody(),
                        Collections.singletonMap("employee", employee));
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("Email notification error", e);
        }
    }
}
