package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Flashcards.Testing.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long> {
}