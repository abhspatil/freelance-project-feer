package com.patil.freelanceApp.services;

import com.patil.freelanceApp.models.UserInfo;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Async
@Component
public class SendGridEmailer {

    @Value("${Email.from}")
    public String From;

    @Value("${Twilio.SendGrid_Api_Key}")
    public String SendGridAPIKey;

    public Logger logger = LoggerFactory.getLogger(SendGridEmailer.class);

    public void sendMail(UserInfo emailInfo) throws IOException {

        Email from = new Email(From);
        Email to = new Email(emailInfo.getEmail()); // use your own email address here

        String subject = "Email Verification @Feer";
        Content content = new Content("text/html", "Your 4 digit key for @Feer Verification : "+
                generate4DigitCode()+"<br/> Valid for 10 minutes"
                );

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SendGridAPIKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        logger.info("Email Verification :: Status :"+response.getStatusCode());

    }

    public static String generate4DigitCode(){
        Random random = new Random();
        int code=0;
        while (code < 1000){
            code = random.nextInt(9999);
        }

        return String.valueOf(code);
    }
}