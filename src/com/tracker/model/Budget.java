package com.tracker.model;

public class Budget {
    private String category;
    private double monthlyLimit;

    public Budget(String category, double monthlyLimit) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public String getCategory() { return category; }
    public double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(double monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    @Override
    public String toString() {
        return String.format("Category: %-12s | Monthly Limit: $%.2f", category, monthlyLimit);
    }
}
