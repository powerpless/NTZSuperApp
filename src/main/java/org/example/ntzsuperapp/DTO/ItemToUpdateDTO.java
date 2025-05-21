package org.example.ntzsuperapp.DTO;


import lombok.Data;

import java.util.List;

@Data
public class ItemToUpdateDTO {
    private String name;
    private Long photoId; // если уже есть
    private String photoType; // если нужно загрузить новый файл
    private byte[] photoBytes;
    private List<ItemAttributeDTO> attributes;
}
