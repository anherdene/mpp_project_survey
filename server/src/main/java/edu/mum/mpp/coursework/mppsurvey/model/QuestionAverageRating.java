package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionAverageRating {
    private Long questionId;
    private Double rating;
    private Long userCount;
}
