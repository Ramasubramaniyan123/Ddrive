package com.training.mybank.ui;

import com.training.mybank.controller.*;
import com.training.mybank.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuUI {

    private final Scanner scanner = new Scanner(System.in);

    private final AuthController authController;
    private final RegistrationController registrationController;
    private final TransactionController transactionController;
    private final ForgotPasswordController forgotPasswordController;
    private final UserController userController;
    private final AuthService authService;

    private MainMenuUI mainMenuUI;
    private UserMenuUI userMenuUI;

    @Autowired
    public MenuUI(
            AuthController authController,
            RegistrationController registrationController,
            TransactionController transactionController,
            ForgotPasswordController forgotPasswordController,
            UserController userController,
            AuthService authService
    ) {
        this.authController = authController;
        this.registrationController = registrationController;
        this.transactionController = transactionController;
        this.forgotPasswordController = forgotPasswordController;
        this.userController = userController;
        this.authService = authService;
    }

    public void start() {

        mainMenuUI = new MainMenuUI(
                scanner,
                authController,
                registrationController,
                forgotPasswordController,
                this
        );

        userMenuUI = new UserMenuUI(
                scanner,
                transactionController,
                userController,
                authService
        );

        mainMenuUI.show();
        shutdown();
    }

    /* ===== ROUTING ===== */

    public void openUserMenu(String username) {
        userMenuUI.show(username);
    }

    private void shutdown() {
        scanner.close();
    }
}
