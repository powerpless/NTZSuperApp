package org.example.ntzsuperapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String description;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ExpenseCategoryType category;

    @ManyToOne
    private User user;
}
