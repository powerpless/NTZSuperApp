package org.example.ntzsuperapp.DTO;

import lombok.Data;

@Data
public class ItemCatalogToUpdateDTO {
    private String ruName;
    private String engName;
    private Double price;
    private String color;
    private Double weight;
    private Long categoryId;
}
