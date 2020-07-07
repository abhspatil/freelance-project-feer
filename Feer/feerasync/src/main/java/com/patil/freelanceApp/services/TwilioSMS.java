package com.patil.freelanceApp.services;

import com.patil.freelanceApp.models.UserInfo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.twilio.example.Example.ACCOUNT_SID;
import static com.twilio.example.Example.AUTH_TOKEN;

@Async
@Component
public class TwilioSMS {

    @Value("${Twilio.PHONE}")
    private static String TWILIO_NUMBER;

    public void sendVerificationSMS(UserInfo userInfo){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(userInfo.getPhone()),
                new PhoneNumber(TWILIO_NUMBER),
                "Your 4 digit key for Phone Verification "+generate4digitKey()+
                    " Valid for 10 minutes")
                .create();
    }

    public static String generate4digitKey(){
        Random random = new Random();
        int code=0;
        while (code < 1000){
            code = random.nextInt(9999);
        }

        return String.valueOf(code);
    }

}
