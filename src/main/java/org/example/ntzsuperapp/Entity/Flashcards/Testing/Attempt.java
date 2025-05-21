package org.example.ntzsuperapp.Entity.Flashcards.Testing;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.Flashcards.Quiz;
import org.example.ntzsuperapp.Entity.MappedSuperClass;
import org.example.ntzsuperapp.Entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Attempt extends MappedSuperClass {
    @ManyToOne
    @JsonIgnore
    private User user; // Пользователь, который прошёл тест

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswers> answers = new ArrayList<>();

    private int score; // Баллы, которые пользователь набрал в тесте

    private int totalQuestions; // Общее количество вопросов в тесте

    private int correctAnswers; // Количество правильных ответов пользователя

    private int wrongAnswers; // Количество неправильных ответов пользователя

    private int skippedQuestions; // Количество пропущенных вопросов пользователем

    private int timeSpent; // Время, затраченное пользователем на тест в секундах

    private boolean isCompleted; // Завершён ли тест пользователем

    private boolean isPassed; // Пройден ли тест пользователем (например, если нужно набрать 70% правильных ответов, то тест считается пройденным)
    @ManyToOne
    private Quiz quiz;
}

