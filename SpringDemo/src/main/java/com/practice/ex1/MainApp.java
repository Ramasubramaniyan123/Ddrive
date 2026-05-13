package com.practice.ex1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        //Starts Spring IOC Container
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ex1-beans.xml");
        //Spring returns the managed bean of notification service
        NotificationService service = context.getBean(NotificationService.class);
        //Calls business method
        service.notifyUser("ram@xcelevate.org", "Welcome to Spring Training");
        context.close();
    }
}
