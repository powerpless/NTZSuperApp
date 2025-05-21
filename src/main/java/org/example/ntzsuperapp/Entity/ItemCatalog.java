package org.example.ntzsuperapp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




// универсальное описание вещи (шаблон)
@Entity
@Table(name = "item_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruName;
    private String engName;
    private Double price;
    private String color;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User catalogOwner;
}
