package org.example.ntzsuperapp.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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
    private boolean hasBeenDeleted;

    @ManyToOne
    @JoinColumn(name = "item_catalog_id")
    private ItemCatalog itemCatalog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private FileDescriptor photo;

    @OneToMany(mappedBy = "dicItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemAttribute> attributes = new ArrayList<>();

}
