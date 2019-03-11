package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.Data;

@Data
public class AnswerRequest {
    private Long surveyId;
    private Long questionId;
    private Long choiceId;
    private String answerText;
}
