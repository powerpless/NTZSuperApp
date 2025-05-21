package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Flashcards.Answers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswersRepository extends JpaRepository<Answers, Long> {
}