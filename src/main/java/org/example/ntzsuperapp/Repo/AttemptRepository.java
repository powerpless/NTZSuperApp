package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Flashcards.Testing.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}