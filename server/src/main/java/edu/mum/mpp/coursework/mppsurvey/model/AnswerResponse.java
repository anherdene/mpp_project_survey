package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AnswerResponse {
    private String question;
    private String textAnswer;
}
