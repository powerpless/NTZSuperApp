package org.example.ntzsuperapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "person_history")
@Data
public class PersonHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long personId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nickName;
    private Date modifiedAt;
}