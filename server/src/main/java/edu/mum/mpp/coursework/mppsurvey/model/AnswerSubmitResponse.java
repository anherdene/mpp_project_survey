package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerSubmitResponse {
    private String surveyName;
    private int questionCount;
    private int answerCount;
}