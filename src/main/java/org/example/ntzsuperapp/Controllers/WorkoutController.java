package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.Entity.Workout;
import org.example.ntzsuperapp.Services.UserService;
import org.example.ntzsuperapp.Services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<Workout> addWorkout(@RequestBody Workout workout, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(workoutService.addWorkout(workout, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping("/progress/{exercise}")
    public ResponseEntity<String> getProgress(@PathVariable String exercise, Authentication authentication) {
        String username = authentication.getName();
        String progress = workoutService.getProgress(username, exercise);
        return ResponseEntity.ok(progress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody Workout workout) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
