package com.practice.ex3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        var processor = context.getBean(MessageProcessor.class);
        processor.processMessage();
        ((AnnotationConfigApplicationContext) context).close();

    }
}
