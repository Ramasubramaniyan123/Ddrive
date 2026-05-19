package com.learning.spring.propertiesload;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        HelloWorldBean helloWorld = ctx.getBean(HelloWorldBean.class);
        helloWorld.printConfig();
    }
}
