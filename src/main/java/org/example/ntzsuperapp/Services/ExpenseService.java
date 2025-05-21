package org.example.ntzsuperapp.Services;

import org.example.ntzsuperapp.Entity.Budget;
import org.example.ntzsuperapp.Entity.Expense;
import org.example.ntzsuperapp.Entity.ExpenseCategoryType;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.BudgetRepo;
import org.example.ntzsuperapp.Repo.ExpenseRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BudgetRepo budgetRepo;

    public Map<String, Object> addExpense(Expense expense, String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        Expense existingExpense = expenseRepo.findByUserAndCategory(user, expense.getCategory());
        double newAmount;

        if (existingExpense != null) {
            newAmount = existingExpense.getAmount() + expense.getAmount();
            existingExpense.setAmount(newAmount);

            if (expense.getDescription() != null && !expense.getDescription().isEmpty()) {
                existingExpense.setDescription(existingExpense.getDescription() + "; " + expense.getDescription());
            }

            expense = expenseRepo.save(existingExpense);
        } else {
            expense.setUser(user);
            newAmount = expense.getAmount();
            expense = expenseRepo.save(expense);
        }

        Budget budget = budgetRepo.findByUserAndCategory(user, expense.getCategory());
        boolean overBudget = false;
        double budgetLimit = 0.0;

        if (budget != null) {
            budgetLimit = budget.getAmount();
            overBudget = newAmount > budgetLimit;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("expense", expense);
        response.put("overBudget", overBudget);
        response.put("budgetLimit", budgetLimit);
        response.put("currentTotal", newAmount);

        return response;
    }

    public List<Expense> getUserExpenses(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        return expenseRepo.findByUser(user);
    }

    public ExpenseCategoryType[] getAvailableCategories() {
        return ExpenseCategoryType.values();
    }

    public Budget setBudget(String username, ExpenseCategoryType category, Double amount) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        Budget existing = budgetRepo.findByUserAndCategory(user, category);
        if (existing != null) {
            existing.setAmount(amount);
            return budgetRepo.save(existing);
        } else {
            Budget budget = new Budget();
            budget.setUser(user);
            budget.setCategory(category);
            budget.setAmount(amount);
            return budgetRepo.save(budget);
        }
    }

    public List<Budget> getUserBudgets(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return budgetRepo.findByUser(user);
    }
}
