package com.practice.ex3;

public class MessageProcessor {
    private final MessageService messageService;

    public MessageProcessor(MessageService messageService) {
        this.messageService = messageService;
    }

    public void processMessage(){
        System.out.println("Process with: "+ messageService.getMessage());
    }
}
