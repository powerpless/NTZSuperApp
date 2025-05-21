package org.example.ntzsuperapp.DTO.FlashCards;

import java.util.List;

//Без Data чтобы быстро скопировать вставить в Android Studio
public class CreateFlashCardDto {
    private String question;
    private List<AnswerDTO> answerlist;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerDTO> getAnswerlist() {
        return answerlist;
    }

    public void setAnswerlist(List<AnswerDTO> answerlist) {
        this.answerlist = answerlist;
    }
}
