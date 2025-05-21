package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByHasBeenDeletedFalse();
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    User findByUsername(String username);
}