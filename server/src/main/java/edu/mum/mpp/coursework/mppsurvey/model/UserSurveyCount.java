package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSurveyCount {
    private Long userId;
    private Long count;
}
