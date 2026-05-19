package com.learning.spring.propertiesload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldBean {

    // Reads "app.name" from the properties file
    @Value("${app.name}")
    private String appName;

    // Reads "app.version"
    @Value("${app.version}")
    private String appVersion;

    // Reads "app.message"
    @Value("${app.message}")
    private String message;

    public void printConfig() {
        System.out.println("--- Using @Value ---");
        System.out.println("App Name: " + appName);
        System.out.println("Version: " + appVersion);
        System.out.println("Message: " + message);

    }
}
