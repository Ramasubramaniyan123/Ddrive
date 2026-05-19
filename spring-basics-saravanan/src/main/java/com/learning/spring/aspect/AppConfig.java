package com.learning.spring.aspect;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
public class AppConfig {

    // 1. Define the raw service (Target)
    @Bean
    public PaymentService rawPaymentService() {
        return new PaymentService();
    }

    // 2. Define the Advice
    @Bean
    public LogAdvice logAdvice() {
        return new LogAdvice();
    }

    // 3. Define the Proxy (The actual bean the client will use)
    @Bean
    public ProxyFactoryBean paymentService() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

        // Set the target object (the raw service)
        proxyFactoryBean.setTarget(rawPaymentService());

        // Set the interceptor/advice names
        //proxyFactoryBean.setInterceptorNames("logAdvice");
        proxyFactoryBean.setInterceptorNames("paymentAdvisor");

        return proxyFactoryBean;
    }



    // 2. Interceptor (Around Advice)
    @Bean
    public AuditInterceptor auditInterceptor() {
        return new AuditInterceptor();
    }

    // 3. Advisor (Connects Interceptor to specific method names)
    @Bean
    public NameMatchMethodPointcutAdvisor paymentAdvisor() {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedNames("checkBalance", "makePayment"); // Apply only to 'makePayment'
        advisor.setAdvice(auditInterceptor());
        return advisor;
    }
}