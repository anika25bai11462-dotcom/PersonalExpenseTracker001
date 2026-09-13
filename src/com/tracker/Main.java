package com.tracker;

import com.tracker.model.Budget;
import com.tracker.model.Transaction;
import com.tracker.model.TransactionType;
import com.tracker.service.ExpenseService;
import com.tracker.service.FileService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final ExpenseService expenseService = new ExpenseService();
    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");
        System.out.println("   PERSONAL EXPENSE & BUDGET TRACKING SYSTEM");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n----------------- MAIN MENU -----------------");
            System.out.println("1. Log Income / Expense");
            System.out.println("2. View All Transactions");
            System.out.println("3. Manage Category Budgets");
            System.out.println("4. View Financial Analytics & Summary");
            System.out.println("5. Export Transactions to CSV File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    logTransactionMenu(scanner);
                    break;
                case "2":
                    viewTransactions();
                    break;
                case "3":
                    manageBudgetsMenu(scanner);
                    break;
                case "4":
                    viewFinancialAnalytics();
                    break;
                case "5":
                    System.out.print("Enter filename for export (e.g., expenses.csv): ");
                    String filename = scanner.nextLine().trim();
                    if (filename.isEmpty()) filename = "expenses.csv";
                    fileService.exportToCSV(filename, expenseService.getAllTransactions());
                    break;
                case "6":
                    running = false;
                    System.out.println("Exiting Application. Have a great financial day!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void logTransactionMenu(Scanner scanner) {
        try {
            System.out.print("Select Type (1. Income | 2. Expense): ");
            String typeInput = scanner.nextLine().trim();
            TransactionType type = typeInput.equals("1") ? TransactionType.INCOME : TransactionType.EXPENSE;

            System.out.print("Enter Category (e.g., Food, Transport, Salary): ");
            String category = scanner.nextLine().trim();

            System.out.print("Enter Amount ($): ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().trim();

            expenseService.addTransaction(LocalDate.now(), type, category, amount, description);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric amount entered.");
        }
    }

    private static void viewTransactions() {
        List<Transaction> list = expenseService.getAllTransactions();
        System.out.println("\n--- TRANSACTION HISTORY ---");
        if (list.isEmpty()) {
            System.out.println("No transactions recorded yet.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private static void manageBudgetsMenu(Scanner scanner) {
        System.out.println("\n--- CATEGORY BUDGET MANAGEMENT ---");
        List<Budget> budgets = expenseService.getAllBudgets();
        budgets.forEach(System.out::println);

        System.out.print("\nEnter category name to set/update budget: ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            try {
                System.out.print("Enter monthly budget limit ($): ");
                double limit = Double.parseDouble(scanner.nextLine().trim());
                expenseService.setBudget(category, limit);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid amount entered.");
            }
        }
    }

    private static void viewFinancialAnalytics() {
        double income = expenseService.getTotalIncome();
        double expenses = expenseService.getTotalExpenses();
        double netSavings = income - expenses;

        System.out.println("\n================ FINANCIAL ANALYTICS ================");
        System.out.printf("Total Income   : $%.2f%n", income);
        System.out.printf("Total Expenses : $%.2f%n", expenses);
        System.out.printf("Net Savings    : $%.2f%n", netSavings);
        System.out.println("----------------------------------------------------");

        System.out.println("Category-wise Breakdown:");
        Map<String, Double> categoryMap = expenseService.getExpensesByCategory();
        if (categoryMap.isEmpty()) {
            System.out.println(" No expense data recorded.");
        } else {
            for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
                double pct = (expenses > 0) ? (entry.getValue() / expenses) * 100 : 0.0;
                System.out.printf(" - %-12s: $%.2f (%.1f%%)%n", entry.getKey(), entry.getValue(), pct);
            }
        }
        System.out.println("====================================================");
    }
}
