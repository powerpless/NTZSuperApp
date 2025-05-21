package org.example.ntzsuperapp.Entity.Flashcards;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.MappedSuperClass;

@Entity
@Getter
@Setter
public class Answers extends MappedSuperClass {
    @ManyToOne
    @JsonIgnore
    private Flashcard flashCard; // Вопрос к ответу, например 1 флэшкарт с несколькими ответами
    private String answer;
    @JsonProperty("isCorrect")
    @JsonAlias({"correct"})
    private boolean isCorrect;
    private String explanation;
    private String imageUrl;
}
