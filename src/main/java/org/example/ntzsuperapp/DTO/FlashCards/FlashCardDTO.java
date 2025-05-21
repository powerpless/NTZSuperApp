package org.example.ntzsuperapp.DTO.FlashCards;

import lombok.Value;
import org.example.ntzsuperapp.Entity.Flashcards.Answers;
import org.example.ntzsuperapp.Entity.Flashcards.Flashcard;

import java.io.Serializable;
import java.util.List;


public class FlashCardDTO implements Serializable {
    Long id;
    String question;
    List<AnswersDto> answers;


    public static class AnswersDto implements Serializable {
        String answer;
        boolean isCorrect;
        String explanation;
        String imageUrl;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswersDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersDto> answers) {
        this.answers = answers;
    }
}