package edu.mum.mpp.coursework.mppsurvey.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuestionRequest {
    @NotBlank
    @Size(min = 2, max = 2)
    private String type;
    @NotBlank
    @Size(min = 2, max = 200)
    private String content;
    private  boolean isOptional;
    private List<ChoiceRequest> choices;
}
