package com.tracker.model;

import java.time.LocalDate;

public class Transaction {
    private String id;
    private LocalDate date;
    private TransactionType type;
    private String category;
    private double amount;
    private String description;

    public Transaction(String id, LocalDate date, TransactionType type, String category, double amount, String description) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public TransactionType getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("ID: %-6s | Date: %s | Type: %-7s | Category: %-12s | Amount: $%.2f | Desc: %s",
                id, date, type, category, amount, description);
    }
}