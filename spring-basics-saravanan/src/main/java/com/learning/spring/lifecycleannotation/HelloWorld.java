package com.learning.spring.lifecycleannotation;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class HelloWorld {

    // 1. Dependency Injection (Field Injection)
    // Replaces ApplicationContextAware interface
    @Autowired
    private ApplicationContext context;

    public HelloWorld() {
        System.out.println("1. Constructor: Instance created.");
    }

    // 2. Initialization Lifecycle Event
    // Replaces InitializingBean interface and init-method XML/Config
    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct: Resources initialized.");
        System.out.println("   (Context available: " + (context != null) + ")");
    }

    // 3. Business Logic
    public void doWork() {
        System.out.println("   --> [WORKING] Doing heavy lifting...");
    }

    // 4. Destruction Lifecycle Event
    // Replaces DisposableBean interface and destroy-method XML/Config
    @PreDestroy
    public void cleanup() {
        System.out.println("3. @PreDestroy: Cleaning up resources (closing DB, sockets, etc).");
    }
}

