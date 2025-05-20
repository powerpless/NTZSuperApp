package org.example.ntzsuperapp.DTO;

import lombok.Data;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;

import java.util.List;

@Data
public class ItemToCreateDTO {
    private String name;
    private Long photoId;
    private Long itemCatalogId; // catalog_id
    private Long userId; // owner
    private List<ItemAttributeDTO> attributes;
}
