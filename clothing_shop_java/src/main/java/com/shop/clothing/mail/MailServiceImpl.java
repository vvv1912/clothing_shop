package com.shop.clothing.mail;

import com.shop.clothing.config.AppProperties;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final AppProperties appProperties;
    @Override
    public void sendEmail( String to, String subject, String content) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom(appProperties.getOwnerEmail());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
