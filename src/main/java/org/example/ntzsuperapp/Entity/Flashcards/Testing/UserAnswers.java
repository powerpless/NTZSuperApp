package org.example.ntzsuperapp.Entity.Flashcards.Testing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.Flashcards.Answers;
import org.example.ntzsuperapp.Entity.MappedSuperClass;
import org.hibernate.mapping.Map;

@Entity
@Getter
@Setter
public class UserAnswers extends MappedSuperClass {
    @ManyToOne
    private Answers answers; // Ответ выбранный пользователем
    @ManyToOne
    @JoinColumn(name = "attempt_id")
    @JsonIgnore
    private Attempt attempt;
    private boolean isCorrect; // Правильный ли ответ
    private long userId; // id пользователя
}
