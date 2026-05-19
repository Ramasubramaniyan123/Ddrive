package com.learning.spring.lifecycleannotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("--- Container Startup ---");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n--- Bean Usage ---");
        HelloWorld bean = context.getBean(HelloWorld.class);
        bean.doWork();

        System.out.println("\n--- Container Shutdown ---");
        context.close(); // Triggers @PreDestroy
    }
}
