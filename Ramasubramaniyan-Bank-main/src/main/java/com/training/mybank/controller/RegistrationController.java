package com.training.mybank.controller;

import com.training.mybank.exception.BankingException;
import com.training.mybank.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class RegistrationController {

    private final RegistrationService registrationService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public void register() {

        System.out.println("\n---- USER REGISTRATION ----");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            String accNo = registrationService.register(
                    username, password, fullName, email);

            System.out.println("Registration successful!");
            System.out.println("Your account number is: " + accNo);

        } catch (BankingException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
}
