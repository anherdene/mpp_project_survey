package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionChoiceCount {
    private Long questionId;
    private Long choiceId;
    private Long count;

}
