package com.patil.freelanceApp.resource;

import com.patil.freelanceApp.models.Certificate;
import com.patil.freelanceApp.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private KafkaTemplate<String, UserInfo> emailKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Certificate> certkafkaTemplate;

    private static final String EMAIL_TOPIC = "EmailService";
    private static final String CERTGEN_TOPIC = "CertGenService";

    @PostMapping("/email")
    public String publishEmail(@RequestBody UserInfo email){
        emailKafkaTemplate.send(EMAIL_TOPIC,email);

        return "Email Published successfully";
    }

    @PostMapping("/cert")
    public String publishCertificate(@RequestBody Certificate certificate){
        certkafkaTemplate.send(CERTGEN_TOPIC,certificate);

        return "Certificate Published successfully";
    }


}
