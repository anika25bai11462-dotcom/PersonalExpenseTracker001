package com.tracker.service;

import com.tracker.model.Transaction;
import com.tracker.util.LoggerUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileService {

    public boolean exportToCSV(String filePath, List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("TransactionID,Date,Type,Category,Amount,Description");
            for (Transaction t : transactions) {
                writer.printf("%s,%s,%s,%s,%.2f,\"%s\"%n",
                        t.getId(),
                        t.getDate(),
                        t.getType(),
                        t.getCategory(),
                        t.getAmount(),
                        t.getDescription());
            }
            LoggerUtil.info("Successfully exported " + transactions.size() + " transactions to " + filePath);
            return true;
        } catch (IOException e) {
            LoggerUtil.error("Failed to export transactions to CSV: " + e.getMessage());
            return false;
        }
    }
}
