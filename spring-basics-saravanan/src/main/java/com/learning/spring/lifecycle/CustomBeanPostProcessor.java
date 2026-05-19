package com.learning.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HelloWorld) {
            System.out.println("5. BeanPostProcessor: Before Initialization.");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HelloWorld) {
            System.out.println("9. BeanPostProcessor: After Initialization.");
        }
        return bean;
    }
}
