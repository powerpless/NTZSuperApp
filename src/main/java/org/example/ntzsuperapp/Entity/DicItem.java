package org.example.ntzsuperapp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// справочник конкретной вещи
@Entity
@Table(name = "dic_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String photoUrl;
    private boolean hasBeenDeleted;

    @ManyToOne
    @JoinColumn(name = "item_catalog_id")
    private ItemCatalog itemCatalog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
