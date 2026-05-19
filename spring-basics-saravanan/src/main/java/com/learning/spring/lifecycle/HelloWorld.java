package com.learning.spring.lifecycle;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HelloWorld implements InitializingBean, DisposableBean, BeanNameAware, ApplicationContextAware {

    private String name;

    // 1. Instantiation
    public HelloWorld() {
        System.out.println("1. Constructor: Bean instance created.");
    }

    // 2. Dependency Injection
    public void setName(String name) {
        this.name = name;
        System.out.println("2. Setter: Properties set (name = " + name + ").");
    }

    // 3. Aware Interfaces
    @Override
    public void setBeanName(String beanName) {
        System.out.println("3. BeanNameAware: Bean name is '" + beanName + "'.");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("4. ApplicationContextAware: Context set.");
    }

    // 5. PostConstruct Annotation
    @PostConstruct
    public void postConstruct() {
        System.out.println("6. @PostConstruct: Annotated init method called.");
    }

    // 6. InitializingBean Interface
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("7. InitializingBean: afterPropertiesSet() called.");
    }

    // 7. Custom Init Method (defined in Config)
    public void customInit() {
        System.out.println("8. Custom Init: Defined in @Bean(initMethod).");
    }

    // --- Bean is now Ready ---

    public void doWork() {
        System.out.println("   --> [WORKING] Bean is doing its job...");
    }

    // --- Destruction Phase ---

    // 9. PreDestroy Annotation
    @PreDestroy
    public void preDestroy() {
        System.out.println("10. @PreDestroy: Annotated destroy method called.");
    }

    // 10. DisposableBean Interface
    @Override
    public void destroy() throws Exception {
        System.out.println("11. DisposableBean: destroy() called.");
    }

    // 11. Custom Destroy Method (defined in Config)
    public void customDestroy() {
        System.out.println("12. Custom Destroy: Defined in @Bean(destroyMethod).");
    }
}