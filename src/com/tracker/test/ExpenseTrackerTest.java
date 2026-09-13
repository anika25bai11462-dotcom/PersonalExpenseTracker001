package com.tracker.test;

import com.tracker.model.TransactionType;
import com.tracker.service.ExpenseService;

import java.time.LocalDate;

public class ExpenseTrackerTest {
    public static void main(String[] args) {
        System.out.println("Running Automated Application Unit Tests...");
        ExpenseService service = new ExpenseService();

        double initialIncome = service.getTotalIncome();
        boolean incomeAdded = service.addTransaction(LocalDate.now(), TransactionType.INCOME, "Freelance", 500.0, "Project Work");
        assert incomeAdded : "Test 1 Failed: Unable to record income transaction";
        assert service.getTotalIncome() == initialIncome + 500.0 : "Test 1 Failed: Total income mismatch";

        double initialExpense = service.getTotalExpenses();
        boolean expenseAdded = service.addTransaction(LocalDate.now(), TransactionType.EXPENSE, "Food", 50.0, "Dinner");
        assert expenseAdded : "Test 2 Failed: Unable to record expense transaction";
        assert service.getTotalExpenses() == initialExpense + 50.0 : "Test 2 Failed: Total expense mismatch";

        boolean negativeAmountHandled = service.addTransaction(LocalDate.now(), TransactionType.EXPENSE, "Food", -10.0, "Invalid");
        assert !negativeAmountHandled : "Test 3 Failed: Allowed negative transaction amount";

        System.out.println("All Automated Unit Tests Passed Successfully!");
    }
}
