package com.training.mybank.controller;

import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.BankingException;
import com.training.mybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserController {

    private final UserService userService;
    private final Scanner sc = new Scanner(System.in);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void viewProfile(String username) {
        UserEntity user = userService.getProfile(username);
        System.out.println("\n========== MY PROFILE ==========");
        System.out.println("Username  : " + user.getUsername());
        System.out.println("Full Name : " + user.getFullName());
        System.out.println("Email     : " + user.getEmail());
        System.out.println("Role      : " + user.getRole());
        System.out.println("Created   : " + user.getCreatedAt().toString().substring(0, 10));
        System.out.println("=================================");
    }

    public void updateProfile(String username) {
        System.out.println("\n--- Update Profile ---");
        System.out.print("Enter New Full Name (leave blank to keep current): ");
        String name = sc.nextLine().trim();
        System.out.print("Enter New Email (leave blank to keep current): ");
        String email = sc.nextLine().trim();

        try {
            if (name.isEmpty() && email.isEmpty()) {
                System.out.println("Nothing updated");
            } else {
                userService.updateProfile(username, name, email);
                System.out.println(" Profile updated successfully.");
            }

        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePassword(String username) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter Old Password: ");
        String oldPass = sc.nextLine();
        System.out.print("Enter New Password: ");
        String newPass = sc.nextLine();

        try {
            userService.changePassword(username, oldPass, newPass);
            System.out.println("Password changed successfully.");
        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }
}
