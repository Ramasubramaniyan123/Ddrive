package com.learning.spring.orm;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserDAO repo = context.getBean(UserDAO.class);

        // 1. Save some users (Hibernate will auto-create the table due to hbm2ddl.auto=create)
        System.out.println("Saving users...");
        //repo.save(new User("Felix", "secret123"));
        //repo.save(new User("Dragon", "admin456"));

        // 2. Retrieve users
        System.out.println("\nReading users from database...");
        List<User> users = repo.findAll();

        // 3. Print
        for (User user : users) {
            System.out.println(user);
        }

        context.close();
    }
}
