package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChoiceRequest {
    @NotBlank
    @Size(max = 200)
    private  String content;
    private boolean isDefault;
}
