package com.feer.feercore.users.model;

public class ConfirmationEmail {
    private String subject;
    private String text;

    public ConfirmationEmail() {}

    public ConfirmationEmail(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
