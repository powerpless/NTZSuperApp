package org.example.ntzsuperapp.Services;

import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Entity.Workout;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.example.ntzsuperapp.Repo.WorkoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepo workoutRepository;
    @Autowired
    private UserRepo userRepository;

    public Workout addWorkout(Workout workout, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    public List<Workout> getAllWorkoutsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return workoutRepository.findAllByUser(user);
    }

    public List<Workout> getWorkoutsByUser(Long id){
        return workoutRepository.findByUserId(id);
    }

    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тренировка не найдена"));
    }

    public Workout updateWorkout(Long id, Workout workoutDetails) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тренировка не найдена"));

        workout.setExercise(workoutDetails.getExercise());
        workout.setSets(workoutDetails.getSets());
        workout.setReps(workoutDetails.getReps());
        workout.setWeight(workoutDetails.getWeight());
        workout.setDate(workoutDetails.getDate());

        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тренировка не найдена"));

        workoutRepository.delete(workout);
    }

    public String getProgress(String username, String exercise) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Пользователь не найден: " + username);
        }

        List<Workout> workouts = workoutRepository.findByUserIdAndExerciseOrderByDateAsc(user.getId(), exercise);

        if (workouts.size() < 2) {
            return "Недостаточно данных для анализа прогресса";
        }

        Workout first = workouts.get(0);
        Workout last = workouts.get(workouts.size() - 1);

        int weightDiff = last.getWeight() - first.getWeight();
        int repsDiff = last.getReps() - first.getReps();
        int setsDiff = last.getSets() - first.getSets();

        double weightPercent = first.getWeight() > 0 ? (weightDiff * 100.0 / first.getWeight()) : 0;
        double repsPercent = first.getReps() > 0 ? (repsDiff * 100.0 / first.getReps()) : 0;
        double setsPercent = first.getSets() > 0 ? (setsDiff * 100.0 / first.getSets()) : 0;

        return String.format("Прогресс по '%s':\n" +
                        "- Вес: %d кг → %d кг (%.2f%%)\n" +
                        "- Повторения: %d → %d (%.2f%%)\n" +
                        "- Сеты: %d → %d (%.2f%%)",
                exercise,
                first.getWeight(), last.getWeight(), weightPercent,
                first.getReps(), last.getReps(), repsPercent,
                first.getSets(), last.getSets(), setsPercent
        );
    }

}
