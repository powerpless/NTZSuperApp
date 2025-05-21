package org.example.ntzsuperapp.DTO.FlashCards;

import java.util.List;

public class QuizAttempt {
    private Long quizId;
    private List<UserAnswers> userAnswers;
    private int timeSpent; // in seconds

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<UserAnswers> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswers> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public static class UserAnswers {
        private Long questionId;
        private Long answerId;

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public Long getAnswerId() {
            return answerId;
        }

        public void setAnswerId(Long answerId) {
            this.answerId = answerId;
        }
    }
}
