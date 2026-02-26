package com.training.mybank.controller;

import com.training.mybank.entity.AccountEntity;
import com.training.mybank.entity.TransactionEntity;
import com.training.mybank.exception.BankingException;
import com.training.mybank.repository.AccountRepository;
import com.training.mybank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final Scanner sc = new Scanner(System.in);

    @Autowired
    public TransactionController(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    /* ---------- DEPOSIT ---------- */

    public void deposit(String username) {
        try {
            System.out.print("Enter deposit amount: ");
            BigDecimal amount = readBigDecimal();

            transactionService.deposit(username, amount);

            System.out.println("\nDeposit Successful");
            System.out.println("Amount Deposited : " + amount);

        } catch (BankingException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }

    /* ---------- WITHDRAW ---------- */

    public void withdraw(String username) {
        try {
            System.out.print("Enter withdrawal amount: ");
            BigDecimal amount = readBigDecimal();

            transactionService.withdraw(username, amount);

            System.out.println("\nWithdrawal Successful");
            System.out.println("Amount Withdrawn : " + amount);

        } catch (BankingException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    /* ---------- TRANSFER ---------- */

    public void transfer(String username) {
        try {
            System.out.print("Enter receiver username: ");
            String toUser = sc.nextLine().trim();

            if (toUser.isEmpty()) {
                throw new BankingException("Receiver username cannot be empty");
            }

            System.out.print("Enter transfer amount: ");
            BigDecimal amount = readBigDecimal();

            transactionService.transfer(username, toUser, amount);

            System.out.println("\nTransfer Successful");
            System.out.println("Transferred " + amount + " to " + toUser);

        } catch (BankingException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    /* ---------- BALANCE ---------- */

    public void balance(String username) {
        BigDecimal balance = transactionService.checkBalance(username);

        System.out.println("\n========== ACCOUNT BALANCE ==========");
        System.out.println("Available Balance : " + balance);
        System.out.println("====================================");
    }

    /* ---------- HISTORY ---------- */

    public void history(String username) {

        List<TransactionEntity> transactions = transactionService.getTransactionHistory(username);
        AccountEntity myAccount = accountRepository.findByUsername(username);

        if (transactions.isEmpty()) {
            System.out.println("\nℹ No transactions found in your history.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                         TRANSACTION HISTORY");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-12s %-15s %-15s %-20s%n",
                "NO", "TYPE", "AMOUNT", "BALANCE", "DATE");
        System.out.println("-".repeat(80));

        int count = 1;
        for (TransactionEntity tx : transactions) {
            BigDecimal displayBalance = null;

            if (tx.getFromAccount() != null && tx.getFromAccount().getId().equals(myAccount.getId())) {
                displayBalance = tx.getFromBalanceAfter();
            } else if (tx.getToAccount() != null && tx.getToAccount().getId().equals(myAccount.getId())) {
                displayBalance = tx.getToBalanceAfter();
            }

            String balanceStr = (displayBalance != null) ? String.format("%.2f", displayBalance) : "N/A";

            System.out.printf("%-5d %-12s %-15.2f %-15s %-20s%n",
                    count++,
                    tx.getTransactionType(),
                    tx.getAmount(),
                    balanceStr,
                    tx.getCreatedAt().toString().substring(0, 16));
        }

        System.out.println("=".repeat(80));
    }

    /* ---------- EXPORT ---------- */

    public void exportHistory(String username) {
        try {
            String filePath = transactionService.exportTransactionHistory(username);
            System.out.println("\nTransaction statement exported successfully!");
            System.out.println("File saved at: " + filePath);
        } catch (BankingException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    /* ---------- SAFE INPUT ---------- */

    private BigDecimal readBigDecimal() {
        while (!sc.hasNextBigDecimal()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        BigDecimal value = sc.nextBigDecimal();
        sc.nextLine();
        return value;
    }
}
