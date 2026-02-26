package com.training.mybank.ui;

import com.training.mybank.controller.TransactionController;
import com.training.mybank.controller.UserController;
import com.training.mybank.service.AuthService;

import java.util.Scanner;

public class UserMenuUI {

    private final Scanner scanner;
    private final TransactionController transactionController;
    private final UserController userController;
    private final AuthService authService;

    public UserMenuUI(
            Scanner scanner,
            TransactionController transactionController,
            UserController userController,
            AuthService authService
    ) {
        this.scanner = scanner;
        this.transactionController = transactionController;
        this.userController = userController;
        this.authService = authService;
    }

    public void show(String username) {

        while (true) {
            System.out.println("\n========== USER DASHBOARD ==========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Balance");
            System.out.println("5. History");
            System.out.println("6. Export History");
            System.out.println("7. My Profile");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            int choice = readChoice();

            try {
                switch (choice) {
                    case 1 -> transactionController.deposit(username);
                    case 2 -> transactionController.withdraw(username);
                    case 3 -> transactionController.transfer(username);
                    case 4 -> transactionController.balance(username);
                    case 5 -> transactionController.history(username);
                    case 6 -> transactionController.exportHistory(username);
                    case 7 -> showProfileMenu(username);
                    case 8 -> {
                        authService.logout(username);
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            pause();
        }
    }

    private void showProfileMenu(String username) {
        while (true) {
            System.out.println("\n========== MY PROFILE MENU ==========");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Back to Dashboard");
            System.out.print("Choose: ");

            int choice = readChoice();
            switch (choice) {
                case 1 -> userController.viewProfile(username);
                case 2 -> userController.updateProfile(username);
                case 3 -> userController.changePassword(username);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
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
