package edu.mum.mpp.coursework.mppsurvey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private int questionCount;
    private String fileType;
    private long size;
}