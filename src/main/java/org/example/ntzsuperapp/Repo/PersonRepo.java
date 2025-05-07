package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Long> {
    List<Person> findAllPersonsByHasBeenDeletedFalse();

    Optional<Person> findPersonByIdAndHasBeenDeletedFalse(Long id);
}