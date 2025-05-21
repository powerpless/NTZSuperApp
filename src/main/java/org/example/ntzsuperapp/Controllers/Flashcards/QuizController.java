package org.example.ntzsuperapp.Controllers.Flashcards;


import org.example.ntzsuperapp.DTO.FlashCards.CreateQuizDTO;
import org.example.ntzsuperapp.DTO.FlashCards.FlashCardDTO;
import org.example.ntzsuperapp.DTO.FlashCards.QuizAttempt;
import org.example.ntzsuperapp.Entity.Flashcards.Quiz;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Services.FlashCard.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService; // Assuming you have a QuizService to handle business logic

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody CreateQuizDTO quiz) {
        if (!quiz.isValid()) {
            return new ResponseEntity<>("Invalid Quiz", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAllQuizzesByCurrentUser() {
        return new ResponseEntity<>(quizService.getAllQuizzesByUserId(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable Long id) {
        return new ResponseEntity<>(quizService.getQuizById(id), HttpStatus.OK);
    }

    @PostMapping("/{quizId}/flash-card/add")
    public ResponseEntity<?> addFlashCard(
            @PathVariable Long quizId,
            @RequestBody FlashCardDTO cardDTO) {
        return new ResponseEntity<>(quizService.addFlashCardToQuiz(quizId, cardDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{quizId}/flash-card/remove")
    public ResponseEntity<?> removeFlashCard(
            @PathVariable Long quizId,
            @RequestBody FlashCardDTO cardDTO) {
        return new ResponseEntity<>(quizService.removeFlashCardFromQuiz(quizId, cardDTO), HttpStatus.OK);
    }

    @PostMapping("/{quizId}/complete")
    public ResponseEntity<?> completeQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizAttempt quiz) {
        return new ResponseEntity<>(quizService.completeQuiz(quizId, quiz), HttpStatus.OK);
    }


}
