package org.example.ntzsuperapp.DTO;

import lombok.Data;

@Data
public class AvatarUploadResponse {
    private String message;
    private String avatarUrl;

    public AvatarUploadResponse(String message, String avatarUrl) {
        this.message = message;
        this.avatarUrl = avatarUrl;
    }
}
