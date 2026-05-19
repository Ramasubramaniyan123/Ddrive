package com.learning.spring.lifecycle;

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
        // Closing the context triggers the destruction lifecycle
        context.close();
    }
}
