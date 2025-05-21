package org.example.ntzsuperapp.Entity.Flashcards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Entity.MappedSuperClass;
import org.example.ntzsuperapp.Entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz extends MappedSuperClass {
    @ManyToOne
    @JsonIgnore
    private User quizOwner;

    @OneToMany(mappedBy = "quiz", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcard = new ArrayList<>(); // Вопросы к тесту, например 100 вопросов, значит 100 флэшкарт с разными ответами

    private boolean isActive = true; // по умолчанию активный

    private boolean isPrivate = false; // по умолчанию приватный

    private String name;

    private String description;

    private String imageUrl; // картинка к тесту, например обложка книги или что-то подобное (Не обязательно)

    @ManyToOne
    private Category category; // Берём предыдущую категорию, чтобы не создавать новую таблицу

    private int questionTimeLimit; // Время на вопрос в секундах, например 30 секунд на вопрос (Обязательно)

    private int percentToPass; // Процент правильных ответов для прохождения теста, например 70 = 70% (Обязательно)
    // Формула -> (количество правильных ответов / количество вопросов) * 100 >= percentToPass
    // Если больше или равно, то тест пройден, иначе не пройден

}
