package org.example.ntzsuperapp.DTO;

import lombok.Data;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;

import java.util.List;

@Data
public class ItemToCreateDTO {
    private String name;
    private Long itemCatalogId; // catalog_id
    private List<ItemAttributeDTO> attributes;
    private String photoType;
    private byte[] photoBytes;
}
