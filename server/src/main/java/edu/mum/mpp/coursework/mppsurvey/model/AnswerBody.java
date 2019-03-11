package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.Data;

import java.util.List;

@Data
public class AnswerBody {
    List<AnswerRequest> answers;
}
