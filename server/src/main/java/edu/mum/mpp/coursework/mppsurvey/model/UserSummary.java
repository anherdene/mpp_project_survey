package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private List<String> roles;
}
