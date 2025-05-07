package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.Entity.Workout;
import org.example.ntzsuperapp.Services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @PostMapping("/{userId}")
    public ResponseEntity<Workout> addWorkout(@PathVariable Long userId, @RequestBody Workout workout){
        return ResponseEntity.ok(workoutService.addWorkout(workout, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping("/progress/{userId}/{exercise}")
    public ResponseEntity<String> getProgress(@PathVariable Long userId, @PathVariable String exercise) {
        return ResponseEntity.ok(workoutService.getProgress(userId, exercise));
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
