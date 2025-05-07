package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.PersonHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonHistoryRepo extends JpaRepository<PersonHistory, Long> {
    List<PersonHistory> findByPersonId(Long personId);
}
