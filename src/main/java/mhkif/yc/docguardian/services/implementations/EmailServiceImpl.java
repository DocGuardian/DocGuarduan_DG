package mhkif.yc.docguardian.services.implementations;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;



    @Override
    public void sendSimpleMailMessage(String name, String by, String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setFrom(by);
            message.setTo(to); // to
            message.setText(body);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void sendMimeMessageWithAttachments(String name, String by, String to, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedImages(String name, String by,String to, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedFiles(String name, String by,String to, String token) {

    }

    @Override
    public void sendHtmlEmail(String name, String by,String to, String token) {

    }

    @Override
    public void sendHtmlEmailWithEmbeddedFiles(String name,String by, String to, String token) {

    }


}