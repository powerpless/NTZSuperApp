package org.example.ntzsuperapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="files")
@Data
public class FileDescriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int size;
    private String type;

    private byte[] bytes;

}
