package org.example.ntzsuperapp.DTO;

import lombok.Data;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;

@Data
public class ItemToCreateDTO {
    private String name;
    private String photoUrl;
    private Long itemCatalogId; // catalog_id
    private Long userId; // owner
}
