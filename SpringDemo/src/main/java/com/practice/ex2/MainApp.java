package com.practice.ex2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ex2-beans.xml");
        DataBaseService service = context.getBean(DataBaseService.class);
        service.connect();
        context.close();
    }
}
