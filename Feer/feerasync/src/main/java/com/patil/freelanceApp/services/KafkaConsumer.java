package com.patil.freelanceApp.services;

import com.patil.freelanceApp.models.Certificate;
import com.patil.freelanceApp.models.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class KafkaConsumer {

    @Autowired
    SendGridEmailer sendGridEmailer;

    @Autowired
    TwilioSMS twilioSMS;

    @Value("${django.cert.generate.url}")
    public String certificateGenerateUrl;

    public Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "EmailService", groupId = "group_id",
            containerFactory = "emailKafkaListenerFactory")
    public void consumeEmail(UserInfo userInfo) throws IOException {

        logger.info("Consumed message: " + userInfo);

        sendGridEmailer.sendMail(userInfo);
        twilioSMS.sendVerificationSMS(userInfo);
    }

    @KafkaListener(topics = "CertGenService", groupId = "group_id",
            containerFactory = "certificateKafkaListenerContainerFactory")
    public void consumeCertZen(Certificate certificate) {

        logger.info("Consumed certificate generation : " + certificate);

        RestTemplate restTemplate = new RestTemplate();
        String response
                = restTemplate.postForObject(certificateGenerateUrl, certificate,String.class);

        logger.info("Certificate Status for userId "+certificate.getUserId()+" :: "+response);
    }
}