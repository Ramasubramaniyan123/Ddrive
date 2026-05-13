package com.practice.ex3;

public class SmsMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Sms Message Received";
    }
}
