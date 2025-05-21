package org.example.ntzsuperapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budget")
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExpenseCategoryType category;

    private Double amount;

    @ManyToOne
    private User user;
}
