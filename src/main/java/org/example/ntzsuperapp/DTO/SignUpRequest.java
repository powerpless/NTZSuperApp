package org.example.ntzsuperapp.DTO;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nickName;
}
