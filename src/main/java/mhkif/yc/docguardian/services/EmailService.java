package mhkif.yc.docguardian.services;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String subject, String body);
    void sendMimeMessageWithAttachments(String name, String to, String token);
    void sendMimeMessageWithEmbeddedImages(String name, String to, String token);
    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);

}