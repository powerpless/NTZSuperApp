package org.example.ntzsuperapp.DTO;


import lombok.Data;

import java.util.List;

@Data
public class ItemToUpdateDTO {
    private String name;
    private Long photoId;
    private List<ItemAttributeDTO> attributes;
}
