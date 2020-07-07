package com.feer.feercore.users.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValiadateUser{

    private User user;

    public static final String PHONE_VERIFICATION = "^[+0-9-\\(\\)\\s]*{6,14}$";

    public ValiadateUser(){}

    public ValiadateUser(User user){
        this.user = user;
    }

    public boolean validate(User user){
        if(user==null){
            return false;
        }

        if(!isValidEmail(user.getEmailId())){
            return false;
        }

        if(!isValidPhoneNumber(user.getPhoneNumber())){
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile(PHONE_VERIFICATION);
        Matcher m = p.matcher(phoneNumber);
        boolean isPhoneValid = m.matches();

        if(!isPhoneValid)
        {
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}

