package com.learning.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Request the bean twice
        HelloWorldBean bean1 = context.getBean(HelloWorldBean.class);
        bean1.sayHello();

        HelloWorldBean bean2 = context.getBean(HelloWorldBean.class);
        //bean1.sayHello();

        // Verify if they are the same instance
        System.out.println("Is bean1 the same instance as bean2? " + (bean1 == bean2));
    }
}
