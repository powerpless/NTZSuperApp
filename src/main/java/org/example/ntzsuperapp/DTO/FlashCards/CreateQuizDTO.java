package org.example.ntzsuperapp.DTO.FlashCards;

import java.util.List;

public class CreateQuizDTO {
    private String name;
    private String description;
    private Long categoryId;
    private List<FlashCardDTO> flashcards; // Если null, то значит будут добавлены позже
    private String imageUrl; // Если null, то значит будет возможно добавлен позже юзером

    public boolean isValid() {
        return name != null && !name.isEmpty() &&
                description != null && !description.isEmpty() &&
                categoryId != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<FlashCardDTO> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashCardDTO> flashcards) {
        this.flashcards = flashcards;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
