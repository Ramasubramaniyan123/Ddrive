package com.training.mybank.ui;

import com.training.mybank.entity.AdminEntity;
import com.training.mybank.entity.AccountEntity;
import com.training.mybank.entity.TransactionEntity;
import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.BankingException;
import com.training.mybank.service.AdminService;
import com.training.mybank.service.AdminService.BankSummary;
import com.training.mybank.service.AdminService.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class AdminMenuUI {

    private final Scanner scanner;
    private final AdminService adminService;

    public AdminMenuUI(Scanner scanner, AdminService adminService) {
        this.scanner = scanner;
        this.adminService = adminService;
    }

    public void show(String adminUsername) {
        while (true) {
            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Freeze User");
            System.out.println("4. Unfreeze User");
            System.out.println("5. View Bank Details");
            System.out.println("6. View User Details");
            System.out.println("7. Export Transaction History");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            int choice = readChoice();

            try {
                
                switch (choice) {
                    case 1:
                        addUser(adminUsername);
                        break;
                    case 2:
                        deleteUser(adminUsername);
                        break;
                    case 3:
                        freezeUser(adminUsername);
                        break;
                    case 4:
                        unfreezeUser(adminUsername);
                        break;
                    case 5:
                        viewBankDetails();
                        break;
                    case 6:
                        viewUserDetails(adminUsername);
                        break;
                    case 7:
                        exportTransactionHistory(adminUsername);
                        break;
                    case 8:
                        System.out.println("Admin logged out.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (BankingException e) {
                System.out.println("Error: " + e.getMessage());
            }

            pause();
        }
    }

    private void addUser(String adminUsername) {
        System.out.println("\n--- Add New User ---");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine().trim();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter initial balance (0 for empty account): ");
        BigDecimal initialBalance = readBigDecimal();

        try {
            UserEntity user = adminService.addUser(adminUsername, username, password, fullName, email, initialBalance);
            System.out.println("\n✓ User created successfully!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Initial Balance: " + initialBalance);
        } catch (BankingException e) {
            System.out.println("Failed to create user: " + e.getMessage());
        }
    }

    private void deleteUser(String adminUsername) {
        System.out.println("\n--- Delete User ---");
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        System.out.print("Are you sure you want to delete user '" + username + "'? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        try {
            adminService.deleteUser(adminUsername, username);
            System.out.println("✓ User '" + username + "' deleted successfully.");
        } catch (BankingException e) {
            System.out.println("Failed to delete user: " + e.getMessage());
        }
    }

    private void freezeUser(String adminUsername) {
        System.out.println("\n--- Freeze User Account ---");
        System.out.print("Enter username to freeze: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        try {
            adminService.freezeUser(adminUsername, username);
            System.out.println("✓ User '" + username + "' account frozen successfully.");
            System.out.println("The user can no longer deposit, withdraw, or transfer funds.");
        } catch (BankingException e) {
            System.out.println("Failed to freeze user: " + e.getMessage());
        }
    }

    private void unfreezeUser(String adminUsername) {
        System.out.println("\n--- Unfreeze User Account ---");
        System.out.print("Enter username to unfreeze: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        try {
            adminService.unfreezeUser(adminUsername, username);
            System.out.println("✓ User '" + username + "' account unfrozen successfully.");
            System.out.println("The user can now perform all transactions.");
        } catch (BankingException e) {
            System.out.println("Failed to unfreeze user: " + e.getMessage());
        }
    }

    private void viewBankDetails() {
        System.out.println("\n========== BANK DETAILS ==========");
        
        BankSummary summary = adminService.getBankSummary();
        
        System.out.println("Total Bank Balance: " + summary.getTotalBalance());
        System.out.println("Total Number of Users: " + summary.getTotalUsers());
        System.out.println("Number of Frozen Accounts: " + summary.getFrozenUsers());
        System.out.println("Number of Active Accounts: " + (summary.getTotalUsers() - summary.getFrozenUsers()));
        System.out.println("==================================");
    }

    private void viewUserDetails(String adminUsername) {
        System.out.print("Enter username to view details: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        try {
            UserDetails details = adminService.getUserDetails(username);

            // Display user profile
            System.out.println("\n========== USER PROFILE ==========");
            System.out.println("Username  : " + details.getUser().getUsername());
            System.out.println("Full Name : " + details.getUser().getFullName());
            System.out.println("Email     : " + details.getUser().getEmail());
            System.out.println("Role      : " + details.getUser().getRole());
            System.out.println("Created   : " + details.getUser().getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            // Display account balance
            System.out.println("\n========== ACCOUNT DETAILS ==========");
            System.out.println("Account Number: " + details.getAccount().getAccountNumber());
            System.out.println("Balance       : " + details.getAccount().getBalance());
            System.out.println("Status        : " + details.getAccount().getStatus());

            // Display transaction history
            System.out.println("\n========== TRANSACTION HISTORY ==========");
            List<TransactionEntity> transactions = details.getTransactions();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.printf("%-5s %-12s %-15s %-15s %-20s%n",
                        "NO", "TYPE", "AMOUNT", "BALANCE", "DATE");
                System.out.println("-".repeat(80));

                int count = 1;
                for (TransactionEntity tx : transactions) {
                    BigDecimal displayBalance = null;

                    if (tx.getFromAccount() != null && tx.getFromAccount().getId().equals(details.getAccount().getId())) {
                        displayBalance = tx.getFromBalanceAfter();
                    } else if (tx.getToAccount() != null && tx.getToAccount().getId().equals(details.getAccount().getId())) {
                        displayBalance = tx.getToBalanceAfter();
                    }

                    String balanceStr = (displayBalance != null) ? String.format("%.2f", displayBalance) : "N/A";
                    String formattedDate = tx.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    System.out.printf("%-5d %-12s %-15.2f %-15s %-20s%n",
                            count++,
                            tx.getTransactionType(),
                            tx.getAmount(),
                            balanceStr,
                            formattedDate);
                }
                System.out.println("=".repeat(80));
            }

        } catch (BankingException e) {
            System.out.println("Error retrieving user details: " + e.getMessage());
        }
    }

    private void exportTransactionHistory(String adminUsername) {
        System.out.println("\n--- EXPORT TRANSACTION HISTORY ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();

        if (username.isEmpty() || accountNumber.isEmpty()) {
            System.out.println("Username and account number cannot be empty.");
            return;
        }

        try {
            UserDetails details = adminService.getUserDetails(username);
            
            // Verify account number matches
            if (!details.getAccount().getAccountNumber().equals(accountNumber)) {
                System.out.println("Account number does not match the user's account.");
                return;
            }

            // Generate filename with timestamp
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "exports/" + username + "_transactions_" + timestamp + ".txt";
            
            // Create exports directory if it doesn't exist
            java.io.File exportsDir = new java.io.File("exports");
            if (!exportsDir.exists()) {
                exportsDir.mkdirs();
            }

            // Write transaction history to file
            try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
                writer.println("========== TRANSACTION HISTORY ==========");
                writer.println("Username: " + username);
                writer.println("Account Number: " + accountNumber);
                writer.println("Export Date: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                writer.println("Current Balance: " + details.getAccount().getBalance());
                writer.println("Account Status: " + details.getAccount().getStatus());
                writer.println("==========================================");
                writer.println();

                List<TransactionEntity> transactions = details.getTransactions();
                
                if (transactions.isEmpty()) {
                    writer.println("No transactions found.");
                } else {
                    writer.printf("%-5s %-12s %-15s %-15s %-20s%n",
                            "NO", "TYPE", "AMOUNT", "BALANCE", "DATE");
                    writer.println("-".repeat(80));

                    int count = 1;
                    for (TransactionEntity tx : transactions) {
                        BigDecimal displayBalance = null;

                        if (tx.getFromAccount() != null && tx.getFromAccount().getId().equals(details.getAccount().getId())) {
                            displayBalance = tx.getFromBalanceAfter();
                        } else if (tx.getToAccount() != null && tx.getToAccount().getId().equals(details.getAccount().getId())) {
                            displayBalance = tx.getToBalanceAfter();
                        }

                        String balanceStr = (displayBalance != null) ? String.format("%.2f", displayBalance) : "N/A";
                        String formattedDate = tx.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                        writer.printf("%-5d %-12s %-15.2f %-15s %-20s%n",
                                count++,
                                tx.getTransactionType(),
                                tx.getAmount(),
                                balanceStr,
                                formattedDate);
                    }
                    writer.println("=".repeat(80));
                }
                
                writer.println();
                writer.println("=== EXPORT SUMMARY ===");
                writer.println("Total Transactions: " + transactions.size());
                writer.println("Export completed by admin: " + adminUsername);
            }

            System.out.println("✓ Transaction history exported successfully!");
            System.out.println("File saved as: " + filename);

        } catch (BankingException e) {
            System.out.println("Error exporting transaction history: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private BigDecimal readBigDecimal() {
        while (!scanner.hasNextBigDecimal()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        BigDecimal value = scanner.nextBigDecimal();
        scanner.nextLine();
        return value;
    }

    private int readChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
