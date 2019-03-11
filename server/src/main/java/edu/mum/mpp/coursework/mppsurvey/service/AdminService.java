package edu.mum.mpp.coursework.mppsurvey.service;

import edu.mum.mpp.coursework.mppsurvey.exception.FileStorageException;
import edu.mum.mpp.coursework.mppsurvey.model.BatchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    SurveyService surveyService;

    /**
     *
     * @param file
     * @return
     */
    public int saveToSurvey(MultipartFile file){
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        List<BatchModel> records = new ArrayList<>();
        // read from cvs stream
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if(values.length==2){
                    records.add(new BatchModel(values[0],values[1]));
                }else if(values.length==6){
                    records.add(new BatchModel(values[0],values[1],values[2],values[3],values[4],values[5],null));
                }else if(values.length==7){
                    records.add(new BatchModel(values[0],values[1],values[2],values[3],values[4],values[5],values[6]));
                }

            }
            // store survey whole
            surveyService.loadFromBatch(records);

            return records.size();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public ResponseEntity generateReportFromSurvey(){
        return ResponseEntity.ok(null);
    }

    public ResponseEntity generateReportFromUser(){
        return ResponseEntity.ok(null);
    }
}
