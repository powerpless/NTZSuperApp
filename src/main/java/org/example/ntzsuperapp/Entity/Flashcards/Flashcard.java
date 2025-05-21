package org.example.ntzsuperapp.Entity.Flashcards;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.MappedSuperClass;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Flashcard extends MappedSuperClass {
    @ManyToOne
    @JsonIgnore
    private Quiz quiz; // Вопрос к тесту, например 1 флэшкарт с несколькими ответами
    private String question;
    @OneToMany(mappedBy = "flashCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answers> answers = new ArrayList<>(); // Выбор между несколькими вариантами

    private long correctAnswerId; // id правильного ответа

    public void addAnswer(Answers answer) {
        answers.add(answer);
        answer.setFlashCard(this);
    }

}
