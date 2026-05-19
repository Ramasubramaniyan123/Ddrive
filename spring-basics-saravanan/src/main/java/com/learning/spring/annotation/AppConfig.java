package com.learning.spring.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.learning.spring.annotation")
public class AppConfig {
    // No manual bean definitions needed here because we used @ComponentScan
}
