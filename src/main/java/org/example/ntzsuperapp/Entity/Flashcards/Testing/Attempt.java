package org.example.ntzsuperapp.Entity.Flashcards.Testing;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.MappedSuperClass;
import org.example.ntzsuperapp.Entity.User;

import java.util.List;

@Entity
@Getter
@Setter
public class Attempt extends MappedSuperClass {
    @ManyToOne
    private User user; // Пользователь, который прошёл тест

    @OneToMany
    private List<UserAnswers> answers; // Ответы пользователя на вопросы теста

    private int score; // Баллы, которые пользователь набрал в тесте

    private int totalQuestions; // Общее количество вопросов в тесте

    private int correctAnswers; // Количество правильных ответов пользователя

    private int wrongAnswers; // Количество неправильных ответов пользователя

    private int skippedQuestions; // Количество пропущенных вопросов пользователем

    private int timeSpent; // Время, затраченное пользователем на тест в секундах

    private boolean isCompleted; // Завершён ли тест пользователем

    private boolean isPassed; // Пройден ли тест пользователем (например, если нужно набрать 70% правильных ответов, то тест считается пройденным)
}

