package org.example.ntzsuperapp.DTO;

import lombok.Data;

@Data
public class FileDTO {
    private String name;
    private int size;
    private String type;

    private byte[] bytes;
}
