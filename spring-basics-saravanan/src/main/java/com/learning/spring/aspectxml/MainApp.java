package com.learning.spring.aspectxml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Spring AOP is the engine (Runtime Proxies).

// AspectJ provides the annotation style and parsing logic.

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("BeansWithAspect.xml");

        // Retrieve the Proxy Bean
        PaymentService service = (PaymentService) context.getBean("paymentService");

        service.makePayment();
        service.checkBalance();
    }
}
