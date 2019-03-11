package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerRequest {
    @NotBlank
    private Long surveyId;
    @NotBlank
    private Long questionId;
    private Long choiceId;
    private String textAnswer;
}
