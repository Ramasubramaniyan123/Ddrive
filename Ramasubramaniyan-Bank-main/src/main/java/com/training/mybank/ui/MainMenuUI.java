package com.training.mybank.ui;

import com.training.mybank.controller.AuthController;
import com.training.mybank.controller.ForgotPasswordController;
import com.training.mybank.controller.RegistrationController;

import com.training.mybank.entity.UserEntity;

import java.util.Scanner;

public class MainMenuUI {

    private final Scanner scanner;
    private final AuthController authController;
    private final RegistrationController registrationController;
    private final ForgotPasswordController forgotPasswordController;
    private final MenuUI menuUI;

    public MainMenuUI(
            Scanner scanner,
            AuthController authController,
            RegistrationController registrationController,
            ForgotPasswordController forgotPasswordController,
            MenuUI menuUI) {
        this.scanner = scanner;
        this.authController = authController;
        this.registrationController = registrationController;
        this.forgotPasswordController = forgotPasswordController;
        this.menuUI = menuUI;
    }

    public void show() {

        while (true) {
            System.out.println("\n========== RAM's BANK ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = readChoice();

            switch (choice) {

                case 1 -> {
                    registrationController.register();
                    pause();
                }

                case 2 -> loginFlow();

                case 3 -> {
                    forgotPasswordController.forgotPassword();
                    pause();
                }

                case 4 -> {
                    System.out.println("Thank you for using MyBank.");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void loginFlow() {
        try {
            UserEntity user = authController.login();

            // If user is null, it means they chose 'exit' in the login loop
            if (user == null) {
                return;
            }

            System.out.println("Login successful");

            menuUI.openUserMenu(user.getUsername());

        } catch (com.training.mybank.exception.AuthenticationFailedException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            pause();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            pause();
        }
    }

    private int readChoice() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private void pause() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
