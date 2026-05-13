package util;

import java.util.List;

import model.Account;
import model.Customer;
import model.Transaction;

/**
 * ConsoleFormatter — Terminal output formatting utility.
 *
 * Centralizes all console display logic so the main menu class
 * stays clean and focused on flow control only.
 * Produces aligned, readable tabular output in the terminal.
 */
public class ConsoleFormatter {

    // ANSI color codes for terminal output
    public static final String RESET  = "\u001B[0m";
    public static final String GREEN  = "\u001B[32m";
    public static final String RED    = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN   = "\u001B[36m";
    public static final String BOLD   = "\u001B[1m";

    private ConsoleFormatter() {}

    // ----------------------------------------------------------------
    // Generic helpers
    // ----------------------------------------------------------------

    public static void printSuccess(String message) {
        System.out.println(GREEN + "✔ SUCCESS: " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "✘ ERROR: " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠ WARNING: " + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(CYAN + "ℹ " + message + RESET);
    }

    public static void printDivider() {
        System.out.println("─".repeat(65));
    }

    public static void printHeader(String title) {
        System.out.println();
        printDivider();
        System.out.println(BOLD + CYAN + "  " + title + RESET);
        printDivider();
    }

    // ----------------------------------------------------------------
    // Customer display
    // ----------------------------------------------------------------

    public static void printCustomer(Customer c) {
        printHeader("CUSTOMER DETAILS");
        System.out.printf("  %-20s : %d%n",   "Customer ID",  c.getCustomerId());
        System.out.printf("  %-20s : %s%n",   "Full Name",    c.getFullName());
        System.out.printf("  %-20s : %s%n",   "Email",        c.getEmail());
        System.out.printf("  %-20s : %s%n",   "Phone",        c.getPhone());
        System.out.printf("  %-20s : %s%n",   "Member Since", c.getCreatedAt());
        printDivider();
    }

    // ----------------------------------------------------------------
    // Account display
    // ----------------------------------------------------------------

    public static void printAccount(Account a) {
        printHeader("ACCOUNT DETAILS");
        System.out.printf("  %-20s : %d%n",     "Account ID",     a.getAccountId());
        System.out.printf("  %-20s : %s%n",     "Account Number", a.getAccountNumber());
        System.out.printf("  %-20s : %s%n",     "Account Type",   a.getAccountType());
        System.out.printf("  %-20s : %s%.2f%n", "Balance",
            GREEN, a.getBalance());
        System.out.print(RESET);
        System.out.printf("  %-20s : %s%n",     "Opened On",      a.getCreatedAt());
        printDivider();
    }

    public static void printAccountList(List<Account> accounts) {
        if (accounts.isEmpty()) {
            printWarning("No accounts found.");
            return;
        }
        printHeader("ACCOUNT LIST");
        System.out.printf("  %-5s  %-22s  %-10s  %12s%n",
            "ID", "Account Number", "Type", "Balance");
        printDivider();
        for (Account a : accounts) {
            System.out.printf("  %-5d  %-22s  %-10s  %12.2f%n",
                a.getAccountId(),
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance()
            );
        }
        printDivider();
    }

    // ----------------------------------------------------------------
    // Transaction display
    // ----------------------------------------------------------------

    public static void printTransactionList(List<Transaction> txns) {
        if (txns.isEmpty()) {
            printWarning("No transactions found.");
            return;
        }
        printHeader("TRANSACTION HISTORY");
        System.out.printf("  %-6s  %-12s  %-22s  %-22s  %12s  %s%n",
            "ID", "Type", "From Account", "To Account", "Amount", "Time");
        printDivider();
        for (Transaction t : txns) {
            String color = switch (t.getTransactionType()) {
                case DEPOSIT    -> GREEN;
                case WITHDRAWAL -> RED;
                case TRANSFER   -> YELLOW;
            };
            System.out.printf("  %-6d  %s%-12s%s  %-22s  %-22s  %12.2f  %s%n",
                t.getTransactionId(),
                color, t.getTransactionType(), RESET,
                t.getFromAccount() != null ? t.getFromAccount() : "EXTERNAL",
                t.getToAccount()   != null ? t.getToAccount()   : "EXTERNAL",
                t.getAmount(),
                t.getTransactionTime()
            );
        }
        printDivider();
    }

    // ----------------------------------------------------------------
    // Main menu
    // ----------------------------------------------------------------

    public static void printMainMenu() {
        System.out.println();
        System.out.println(BOLD + CYAN +
            "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + CYAN +
            "║          JDBC BANKING MANAGEMENT SYSTEM                  ║" + RESET);
        System.out.println(BOLD + CYAN +
            "╚══════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println("  " + YELLOW + "── Customer Operations ──" + RESET);
        System.out.println("   1. Create Customer");
        System.out.println("   2. View Customer Details");
        System.out.println();
        System.out.println("  " + YELLOW + "── Account Operations ──" + RESET);
        System.out.println("   3. Create Account");
        System.out.println("   4. View Account Details");
        System.out.println("   5. Delete Account");
        System.out.println();
        System.out.println("  " + YELLOW + "── Transaction Operations ──" + RESET);
        System.out.println("   6. Deposit Money");
        System.out.println("   7. Withdraw Money");
        System.out.println("   8. Transfer Money");
        System.out.println("   9. View Transaction History");
        System.out.println();
        System.out.println("  " + YELLOW + "── Advanced Features ──" + RESET);
        System.out.println("  10. Batch Insert Demo Data");
        System.out.println("  11. Export Transactions to CSV");
        System.out.println("  12. Calculate Savings Interest");
        System.out.println("  13. View Database Metadata");
        System.out.println("  14. Transfer via Stored Procedure");
        System.out.println();
        System.out.println("   0. Exit");
        System.out.println();
        System.out.print(BOLD + "  Enter your choice: " + RESET);
    }
}
