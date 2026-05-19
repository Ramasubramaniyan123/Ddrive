package com.learning.spring.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Spring AOP is the engine (Runtime Proxies).

// AspectJ provides the annotation style and parsing logic.

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Note: We request the bean named "paymentService", which is the Proxy
        PaymentService service = (PaymentService) context.getBean("paymentService");

        service.makePayment();
        service.checkBalance();

        context.close();
    }
}
