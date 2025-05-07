package org.example.ntzsuperapp.DTO;

import lombok.Data;

@Data
public class Login {
    private boolean success;
    private String token;
    private long expirationTime;
    private String username;
    private String message;

    public Login(boolean success, String token, long expirationTime, String username, String message) {
        this.success = success;
        this.token = token;
        this.expirationTime = expirationTime;
        this.username = username;
        this.message = message;
    }
}