package com.training.mybank.controller;

import com.training.mybank.entity.UserEntity;
import com.training.mybank.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AuthController {

    private final AuthService authService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public UserEntity login() {

        while (true) {
            System.out.println("\n---- LOGIN ----");

            String username;
            while (true) {
                System.out.print("Username: ");
                username = scanner.nextLine();

                if (username.trim().equalsIgnoreCase("exit")) {
                    return null; // Return null to indicate cancellation
                }

                try {
                    // Check if username exists before asking for password
                    authService.verifyUserExists(username);
                    break; // Valid username found, exit inner loop
                } catch (com.training.mybank.exception.AuthenticationFailedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please try again or type 'exit' to return to the main menu.");
                }
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            try {
                return authService.login(username, password);
            } catch (com.training.mybank.exception.AuthenticationFailedException e) {
                System.out.println(e.getMessage());
                System.out.println("Returning to username prompt...");
            }
        }
    }
}
