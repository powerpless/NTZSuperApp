package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.Entity.Budget;
import org.example.ntzsuperapp.Entity.Expense;
import org.example.ntzsuperapp.Entity.ExpenseCategoryType;
import org.example.ntzsuperapp.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(expenseService.addExpense(expense, username));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(expenseService.getUserExpenses(username));
    }

    @GetMapping("/categories")
    public ResponseEntity<ExpenseCategoryType[]> getAvailableCategories() {
        return ResponseEntity.ok(expenseService.getAvailableCategories());
    }

    @PostMapping("/budgets")
    public ResponseEntity<Budget> setBudget(@RequestParam ExpenseCategoryType category,
                                            @RequestParam Double amount,
                                            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(expenseService.setBudget(username, category, amount));
    }

    @GetMapping("/budgets")
    public ResponseEntity<List<Budget>> getUserBudgets(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(expenseService.getUserBudgets(username));
    }
}
