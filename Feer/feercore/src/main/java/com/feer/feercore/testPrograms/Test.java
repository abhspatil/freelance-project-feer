package com.feer.feercore.testPrograms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static final String PHONE_VERIFICATION = "^[+0-9-\\(\\)\\s]*{6,14}$";

    private static Pattern p;
    private static Matcher m;

    public static void main(String[] args)
    {
        //Phone validation
        p = Pattern.compile(PHONE_VERIFICATION);
        m = p.matcher("+917829025712");
        boolean isPhoneValid = m.matches();

        if(!isPhoneValid)
        {
            System.out.println("The Phone number is NOT valid!");
            return;
        }
        System.out.println("The Phone number is valid!");
    }
}
