package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Flashcards.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
  List<Quiz> findAllByQuizOwnerId(Long id);
}