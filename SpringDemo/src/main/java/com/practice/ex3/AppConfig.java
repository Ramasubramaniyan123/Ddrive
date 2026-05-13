package com.practice.ex3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageService emailMessageService() {
        return new EmailMessageService();
    }

    @Bean
    public MessageService smsMessageService() {
        return new SmsMessageService();
    }

    @Bean
    public MessageProcessor messageProcessor() {
        return new MessageProcessor(smsMessageService());
    }
}
