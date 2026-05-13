package com.practice.ex3;

public class EmailMessageService implements MessageService{

    @Override
    public String getMessage() {
        return "Email Message Received";
    }
}
