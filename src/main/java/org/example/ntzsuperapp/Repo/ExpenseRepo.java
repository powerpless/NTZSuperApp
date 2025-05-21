package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Expense;
import org.example.ntzsuperapp.Entity.ExpenseCategoryType;
import org.example.ntzsuperapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Expense findByUserAndCategory(User user, ExpenseCategoryType category);
}
