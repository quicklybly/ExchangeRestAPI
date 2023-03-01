package com.quicklybly.exchangerestapi.services;

import com.quicklybly.exchangerestapi.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${spring.mail.activation-url}")
    private String activationUrl;

    private final JavaMailSender javaMailSender;

    public void sendActivationEmail(MailDTO mailDTO) {
        final String subject = "Account activation";
        final String messageBody = createActivationLetterBody(mailDTO.getId());
        final String emailTo = mailDTO.getEmail();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        javaMailSender.send(mailMessage);
    }

    private String createActivationLetterBody(String id) {
        String message = String.format("To complete registration, follow the link:\n%s",
                activationUrl);
        return message.replace("{id}", id);
    }

}
