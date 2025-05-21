package org.example.ntzsuperapp.DTO.FlashCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Entity.Flashcards.Flashcard;
import org.example.ntzsuperapp.Entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.example.ntzsuperapp.Entity.Flashcards.Quiz}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class QuizDto implements Serializable {
    private Long id;
    private User quizOwner;
    private List<FlashCardDTO> flashcard;
    private boolean isActive;
    private boolean isPrivate;
    private String name;
    private String description;
    private String imageUrl;
    private Category category;
    private int questionTimeLimit;
    private int percentToPass;
}