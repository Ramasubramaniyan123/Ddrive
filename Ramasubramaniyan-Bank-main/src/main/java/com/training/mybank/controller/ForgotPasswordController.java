package com.training.mybank.controller;

import com.training.mybank.exception.InvalidRecoveryDetailsException;
import com.training.mybank.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    public void forgotPassword() {

        System.out.println("\n---- FORGOT PASSWORD ----");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        try {
            forgotPasswordService.resetPassword(
                    username, email, accountNumber, newPassword, confirmPassword);

            System.out.println("Password reset successful!");

        } catch (InvalidRecoveryDetailsException e) {
            System.out.println("Reset failed: " + e.getMessage());
        }
    }
}
