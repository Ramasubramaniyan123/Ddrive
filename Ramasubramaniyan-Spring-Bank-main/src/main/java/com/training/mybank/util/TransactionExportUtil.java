package com.training.mybank.util;

import com.training.mybank.entity.TransactionEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionExportUtil {

    private static final String EXPORT_DIR = "exports";

    public static String exportToText(String username, Long accountId, List<TransactionEntity> transactions)
            throws IOException {
        Path path = Paths.get(EXPORT_DIR);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = EXPORT_DIR + "/" + username + "_statement_" + System.currentTimeMillis() + ".txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("================================================================================");
            writer.println("                         RAM BANK - TRANSACTION STATEMENT");
            writer.println("================================================================================");
            writer.println("Username: " + username);
            writer.println("Generated on: " + LocalDateTime.now().format(formatter));
            writer.println("================================================================================");
            writer.printf("%-20s | %-12s | %-12s | %-12s | %-15s%n",
                    "DATE/TIME", "TYPE", "AMOUNT", "BALANCE", "REMARKS");
            writer.println("--------------------------------------------------------------------------------");

            for (TransactionEntity tx : transactions) {
                java.math.BigDecimal displayBalance = null;

                if (tx.getFromAccount() != null && tx.getFromAccount().getId().equals(accountId)) {
                    displayBalance = tx.getFromBalanceAfter();
                } else if (tx.getToAccount() != null && tx.getToAccount().getId().equals(accountId)) {
                    displayBalance = tx.getToBalanceAfter();
                }

                String balanceStr = (displayBalance != null) ? String.format("%.2f", displayBalance) : "N/A";

                writer.printf("%-20s | %-12s | %-12.2f | %-12s | %-15s%n",
                        tx.getCreatedAt().format(formatter),
                        tx.getTransactionType(),
                        tx.getAmount(),
                        balanceStr,
                        tx.getRemarks() != null ? tx.getRemarks() : "-");
            }
            writer.println("================================================================================");
            writer.println("                                END OF STATEMENT");
            writer.println("================================================================================");
        }

        return fileName;
    }
}
