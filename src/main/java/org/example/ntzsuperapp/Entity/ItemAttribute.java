package org.example.ntzsuperapp.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_attributes")
@Data
public class ItemAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;
    private String value;

    @ManyToOne
    @JoinColumn(name = "dic_item_id")
    @JsonBackReference
    private DicItem dicItem;
}
