package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepo extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Long id);

    List<Workout> findByUserIdAndExerciseOrderByDateAsc(Long userId, String exercise);
    List<Workout> findAllByUser(User user);
}
