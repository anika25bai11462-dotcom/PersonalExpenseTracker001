package com.tracker.service;

import com.tracker.model.Budget;
import com.tracker.model.Transaction;
import com.tracker.model.TransactionType;
import com.tracker.util.LoggerUtil;

import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Map<String, Budget> budgets = new HashMap<>();
    private int idCounter = 100;

    public ExpenseService() {
        setBudget("Food", 500.0);
        setBudget("Transport", 200.0);
        setBudget("Entertainment", 150.0);
        setBudget("Utilities", 300.0);

        addTransaction(LocalDate.now().minusDays(3), TransactionType.INCOME, "Salary", 2500.0, "Monthly Salary");
        addTransaction(LocalDate.now().minusDays(2), TransactionType.EXPENSE, "Food", 45.50, "Grocery Shopping");
        addTransaction(LocalDate.now().minusDays(1), TransactionType.EXPENSE, "Transport", 30.00, "Fuel refill");
    }

    public String generateNextId() {
        return "TX" + (++idCounter);
    }

    public boolean addTransaction(LocalDate date, TransactionType type, String category, double amount, String description) {
        if (amount <= 0) {
            LoggerUtil.error("Transaction amount must be greater than zero.");
            return false;
        }

        String id = generateNextId();
        Transaction transaction = new Transaction(id, date, type, category, amount, description);
        transactions.add(transaction);
        LoggerUtil.info("Recorded " + type + " of $" + amount + " in category '" + category + "'");

        if (type == TransactionType.EXPENSE) {
            checkBudgetAlert(category);
        }
        return true;
    }

    public void setBudget(String category, double limit) {
        if (limit < 0) {
            LoggerUtil.error("Budget limit cannot be negative.");
            return;
        }
        budgets.put(category.toLowerCase(), new Budget(category, limit));
        LoggerUtil.info("Set budget for category '" + category + "' to $" + limit);
    }

    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<String, Double> getExpensesByCategory() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.EXPENSE) {
                categoryTotals.put(t.getCategory(),
                        categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }
        return categoryTotals;
    }

    public void checkBudgetAlert(String category) {
        Budget b = budgets.get(category.toLowerCase());
        if (b == null) return;

        double totalSpent = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE && t.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (totalSpent > b.getMonthlyLimit()) {
            LoggerUtil.error("BUDGET ALERT: Total expenses for '" + category + "' ($" +
                    String.format("%.2f", totalSpent) + ") exceeded limit of $" + b.getMonthlyLimit());
        } else if (totalSpent >= b.getMonthlyLimit() * 0.85) {
            LoggerUtil.info("WARNING: You have used " + String.format("%.1f", (totalSpent / b.getMonthlyLimit()) * 100) +
                    "% of budget for '" + category + "'");
        }
    }

    public List<Budget> getAllBudgets() {
        return new ArrayList<>(budgets.values());
    }
}
