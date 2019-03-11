package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.Data;

@Data
public class BatchModel {
    private String question;
    private String qType;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String isDefault;

    public BatchModel(String question, String qType) {
        this.question = question;
        this.qType = qType;
    }

    public BatchModel(String question, String qType, String answer1, String answer2, String answer3, String answer4, String isDefault) {
        this.question = question;
        this.qType = qType;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.isDefault = isDefault;
    }
}
