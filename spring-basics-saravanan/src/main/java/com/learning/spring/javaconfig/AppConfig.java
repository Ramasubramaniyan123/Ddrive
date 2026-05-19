package com.learning.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name="bean1")
    public HelloWorld helloWorld(){
        return new HelloWorld();
    }
}
