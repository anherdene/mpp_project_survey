package edu.mum.mpp.coursework.mppsurvey.model;

import edu.mum.mpp.coursework.mppsurvey.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveySummary {
    private long id;
    private String title;
    private String description;
    private int qCount;

    public static SurveySummary newInstance(Survey survey){
        return new SurveySummary(survey.getId(),survey.getTitle(),survey.getDescription(),survey.getQuestions().size());
    }
}
