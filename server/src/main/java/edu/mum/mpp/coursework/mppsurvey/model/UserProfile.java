package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private Instant joinedAt;
    private Long surveyCount;
    private Long ratingCount;

}
