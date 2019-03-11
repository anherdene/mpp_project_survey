package edu.mum.mpp.coursework.mppsurvey.controller;

import edu.mum.mpp.coursework.mppsurvey.security.CurrentUser;
import edu.mum.mpp.coursework.mppsurvey.security.UserPrincipal;
import edu.mum.mpp.coursework.mppsurvey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@PreAuthorize("hasRole('USER')")
public class QuestionController {

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/rate")
    public ResponseEntity rate(@CurrentUser UserPrincipal user, @RequestParam Long questionId, @RequestParam Long rate){
        return ResponseEntity.ok(surveyService.rateQuestion(questionId,user.getId(),rate));
    }
}
