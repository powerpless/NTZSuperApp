package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Budget;
import org.example.ntzsuperapp.Entity.ExpenseCategoryType;
import org.example.ntzsuperapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
    Budget findByUserAndCategory(User user, ExpenseCategoryType category);
    List<Budget> findByUser(User user);
}
