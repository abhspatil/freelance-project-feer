package com.feer.feercore.users.service;

import com.feer.feercore.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class VerifyMail implements Runnable{
    private User user;
    private String baseUrl;

    public VerifyMail() {}

    public VerifyMail(User user,String baseUrl){
        this.user=user;
    }

    @Override
    public void run() {

        EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        EmailCfg emailCfg = new EmailCfg();

        System.out.println(encryptDecrypt);

        String id = user.getEmailId();
        String encryptedId = encryptDecrypt.encrypt(id);

        baseUrl += "/api/v1/users/activate/"+encryptedId;

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailCfg.getUsername());
        mailMessage.setTo(user.getEmailId());
        mailMessage.setSubject("Email Confirmation from @Feer");
        mailMessage.setText(baseUrl);

        //Send mail
        javaMailSender.send(mailMessage);

        System.out.println("Email sent");
    }
}
