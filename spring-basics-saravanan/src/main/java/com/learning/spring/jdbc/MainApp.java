package com.learning.spring.jdbc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserDAO userDAO = context.getBean(UserDAO.class);

        // 1. Initialize DB (Create table and insert dummy data)
        // In a real app, the table would already exist.
        userDAO.initDb();

        // 2. Read content
        System.out.println("Reading users from database...");
        List<User> users = userDAO.getAllUsers();

        // 3. Print to console
        for (User user : users) {
            System.out.println(user);
        }

        context.close();
    }
}
