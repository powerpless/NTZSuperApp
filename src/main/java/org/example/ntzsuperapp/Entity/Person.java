package org.example.ntzsuperapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nickName;
    private boolean hasBeenDeleted = false;
    @OneToOne(mappedBy = "person")
    private User user;
}
