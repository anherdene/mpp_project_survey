package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.Data;

import javax.validation.constraints.*;

/**
 *
 */
@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
