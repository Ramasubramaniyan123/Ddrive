package com.practice.ex1;

public class EmailService {
    private final String smtpServer;

    public EmailService(String smtpServer) {
        this.smtpServer = smtpServer;
    }
    public void sendEmail(String to, String message){
        System.out.println("Sending email to: "+ to);
        System.out.println("Message: "+ message);
        System.out.println("Via SMTP Server: "+ smtpServer);
    }
}
