package com.learning.spring.lifecycleannotation;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.learning.spring.lifecycleannotation")
public class AppConfig {
    // No @Bean definition needed here thanks to @Component
}
