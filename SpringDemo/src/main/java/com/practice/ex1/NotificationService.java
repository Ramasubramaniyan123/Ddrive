package com.practice.ex1;

public class NotificationService {
    private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }
    public void notifyUser(String email, String notification){
        System.out.println("Notification Service : Passing notification............");
        emailService.sendEmail(email,notification);
    }
}
