package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 */
@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
}
