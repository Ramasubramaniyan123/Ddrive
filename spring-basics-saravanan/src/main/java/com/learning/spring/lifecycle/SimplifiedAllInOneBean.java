package com.learning.spring.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SimplifiedAllInOneBean implements InitializingBean, DisposableBean {

    // 1. Annotation
    @PostConstruct
    public void annotationInit() {
        System.out.println("1. @PostConstruct");
    }

    // 2. Interface
    @Override
    public void afterPropertiesSet() {
        System.out.println("2. InitializingBean.afterPropertiesSet()");
    }

    @Override
    public void destroy() {
        System.out.println("2. DisposableBean.destroy()");
    }

    // 3. Configuration Method (referenced in AppConfig)
    public void customInit() {
        System.out.println("3. customInit()");
    }

    public void customDestroy() {
        System.out.println("3. customDestroy()");
    }

    @PreDestroy
    public void annotationDestroy() {
        System.out.println("1. @PreDestroy");
    }

}
