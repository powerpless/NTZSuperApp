package org.example.ntzsuperapp.Services.FlashCard;

import org.example.ntzsuperapp.DTO.FlashCards.CreateQuizDTO;
import org.example.ntzsuperapp.DTO.FlashCards.FlashCardDTO;
import org.example.ntzsuperapp.DTO.FlashCards.QuizAttempt;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Entity.Flashcards.Answers;
import org.example.ntzsuperapp.Entity.Flashcards.Flashcard;
import org.example.ntzsuperapp.Entity.Flashcards.Quiz;
import org.example.ntzsuperapp.Entity.Flashcards.Testing.Attempt;
import org.example.ntzsuperapp.Entity.Flashcards.Testing.UserAnswers;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.*;
import org.example.ntzsuperapp.Services.AbstractService;
import org.example.ntzsuperapp.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class QuizService extends AbstractService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FlashcardRepository flashcardRepository;
    @Autowired
    private AnswersRepository answersRepository;
    @Autowired
    private AttemptRepository attemptRepository;

    public QuizService(UserRepo userRepo) {
        super(userRepo);
    }

    @Autowired
    private QuizRepository quizRepo;

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    public List<Quiz> getAllQuizzesByUserId() {
        User user = getCurrentUser();
        return quizRepo.findAllByQuizOwnerId(user.getId());
    }

    public List<Flashcard> getAllFlashCardsByQuizId(Long id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return quiz.getFlashcard();
    }

    public Quiz getQuizById(Long id) {
        User user = getCurrentUser();
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        if (quiz.isPrivate() && !quiz.getQuizOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Quiz is private");
        }
        return quiz;
    }

    public Quiz createQuiz(Quiz quiz) {
        User user = getCurrentUser();
        quiz.setQuizOwner(user);
        return quizRepo.save(quiz);
    }

    public Quiz createQuiz(CreateQuizDTO dto) {
        User user = getCurrentUser();

        if (!dto.isValid()) {
            throw new RuntimeException("Quiz is not valid");
        }

        Quiz quiz = new Quiz();
        quiz.setName(dto.getName());
        quiz.setDescription(dto.getDescription());
        quiz.setCategory(getCategoryById(dto.getCategoryId()));
        quiz.setQuizOwner(user);

        List<Flashcard> flashcards = new ArrayList<>();

        for (FlashCardDTO flashCardDTO : dto.getFlashcards()) {
            if (flashCardDTO.getAnswers().isEmpty()) {
                continue;
            }

            Flashcard flashcard = new Flashcard();
            flashcard.setQuestion(flashCardDTO.getQuestion());
            flashcard.setQuiz(quiz); // обязательно указать владельца

            Answers correctAnswer = null;
            for (FlashCardDTO.AnswersDto answersDto : flashCardDTO.getAnswers()) {
                Answers answer = new Answers();
                answer.setAnswer(answersDto.getAnswer());
                answer.setCorrect(answersDto.isCorrect());
                answer.setExplanation(answersDto.getExplanation());
                flashcard.addAnswer(answer); // автоматически связывает
                if (answersDto.isCorrect()) {
                    correctAnswer = answer;
                }
            }

            if (correctAnswer == null) {
                throw new RuntimeException("Flashcard must have a correct answer");
            }

            flashcards.add(flashcard);
        }

        quiz.setFlashcard(flashcards);
        quiz = quizRepo.save(quiz); // сохраняем quiz + flashcards + answers (если cascade есть)

        // теперь можно выставить correctAnswerId (id уже есть)
        for (Flashcard flashcard : quiz.getFlashcard()) {
            flashcard.setCorrectAnswerId(
                    flashcard.getAnswers().stream()
                            .filter(Answers::isCorrect)
                            .findFirst()
                            .map(Answers::getId)
                            .orElse(null)
            );
            flashcardRepository.save(flashcard); // обновляем только id правильного ответа
        }

        return quiz;
    }


    public Quiz addFlashCardToQuiz(Long quizId, FlashCardDTO cardDTO) {
        Quiz quiz = getQuizById(quizId);

        if (cardDTO.getAnswers().isEmpty()) {
            throw new RuntimeException("FlashCard is not valid");
        }
        AtomicReference<Answers> correctAnswerIdHolder = new AtomicReference<>(null);

        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(cardDTO.getQuestion());
        flashcard.setQuiz(quiz);

        for (FlashCardDTO.AnswersDto answersDto : cardDTO.getAnswers()) {
            Answers answer = new Answers();
            answer.setAnswer(answersDto.getAnswer());
            answer.setCorrect(answersDto.isCorrect());
            answer.setExplanation(answersDto.getExplanation());
            flashcard.addAnswer(answer);
            if (answersDto.isCorrect()) {
                correctAnswerIdHolder.set(answer);
            }
        }

        flashcard = flashcardRepository.save(flashcard);
        if (correctAnswerIdHolder.get() == null) {
            throw new RuntimeException("Correct answer not found");
        }
        flashcard.setCorrectAnswerId(correctAnswerIdHolder.get().getId());
        flashcardRepository.save(flashcard);

        quiz.getFlashcard().add(flashcard);
        return quizRepo.save(quiz);

    }


    private Category getCategoryById(Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }


    public Quiz removeFlashCardFromQuiz(Long quizId, FlashCardDTO cardDTO) {
        User user = getCurrentUser();
        Quiz quiz = getQuizById(quizId);
        if (!quiz.getQuizOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You are not the owner of this quiz");
        }
        Flashcard flashcard = quiz.getFlashcard().stream()
                .filter(f -> f.getQuestion().equals(cardDTO.getQuestion()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("FlashCard not found"));
        quiz.getFlashcard().remove(flashcard);
        return quizRepo.save(quiz);
    }

    public Attempt completeQuiz(Long quizId, QuizAttempt quiz) {
        User user = getCurrentUser();
        Quiz quizEntity = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quizEntity.isActive() && quizEntity.isPrivate() &&
                !quizEntity.getQuizOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Quiz is private");
        }

        Attempt attempt = new Attempt();
        attempt.setUser(user);
        attempt.setTimeSpent(quiz.getTimeSpent());
        attempt.setCompleted(true);

        List<UserAnswers> userAnswers = new ArrayList<>();
        int correctAnswers = 0;
        int answeredQuestions = 0;

        for (QuizAttempt.UserAnswers answers : quiz.getUserAnswers()) {
            // Пропущенные (null или -1) мы просто игнорируем
            if (answers.getAnswerId() == null || answers.getAnswerId() <= 0) continue;

            Flashcard flashcard = flashcardRepository.findById(answers.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("FlashCard not found"));
            Answers answerEntity = answersRepository.findById(answers.getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));

            UserAnswers userAnswer = new UserAnswers();
            userAnswer.setAnswers(answerEntity);
            userAnswer.setAttempt(attempt);
            userAnswer.setUserId(user.getId());

            boolean isCorrect = flashcard.getCorrectAnswerId() == (answers.getAnswerId());
            userAnswer.setCorrect(isCorrect);
            userAnswers.add(userAnswer);

            answeredQuestions++;
            if (isCorrect) correctAnswers++;
        }

        int totalQuestions = quizEntity.getFlashcard().size();
        int wrongAnswers = answeredQuestions - correctAnswers;
        int skippedQuestions = totalQuestions - answeredQuestions;

        // процент правильных
        double score = (totalQuestions > 0)
                ? ((double) correctAnswers / totalQuestions) * 100
                : 0;

        boolean isPassed = score >= quizEntity.getPercentToPass();

        attempt.setAnswers(userAnswers);
        attempt.setCorrectAnswers(correctAnswers);
        attempt.setWrongAnswers(wrongAnswers);
        attempt.setSkippedQuestions(skippedQuestions);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setScore((int) score); // если поле int, округляем
        attempt.setPassed(isPassed);
        attempt.setQuiz(quizEntity);

        return attemptRepository.save(attempt);
    }

    public List<Attempt> getQuizAttempts() {
        User user = getCurrentUser();
        return attemptRepository.findAllByUserId(user.getId());
    }
}
