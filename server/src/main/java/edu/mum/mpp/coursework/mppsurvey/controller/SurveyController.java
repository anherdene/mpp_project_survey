package edu.mum.mpp.coursework.mppsurvey.controller;

import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.entity.Survey;
import edu.mum.mpp.coursework.mppsurvey.entity.UserQuestionAnswer;
import edu.mum.mpp.coursework.mppsurvey.exception.AppException;
import edu.mum.mpp.coursework.mppsurvey.model.*;
import edu.mum.mpp.coursework.mppsurvey.repository.SurveyRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserQuestionAnswerRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserQuestionRatingRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserRepository;
import edu.mum.mpp.coursework.mppsurvey.security.CurrentUser;
import edu.mum.mpp.coursework.mppsurvey.security.UserPrincipal;
import edu.mum.mpp.coursework.mppsurvey.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController{
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    UserQuestionRatingRepository userQuestionRatingRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/index")
    public ResponseEntity index(){
        PageRequest pageRequest = PageRequest.of(0,10);
        return ResponseEntity.ok(surveyRepository.findAllByOrderByIdDesc(pageRequest).stream().map(SurveySummary::newInstance));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getSurvey( @PathVariable Long id){
        Optional<Survey> optionalSurvey= surveyRepository.findById(id);
        return ResponseEntity.of(optionalSurvey);
    }

    @GetMapping("/get/{id}/answer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getSurveyAnswer(@CurrentUser UserPrincipal currentUser,  @PathVariable Long id){
        Survey survey= surveyRepository.findById(id).orElseThrow(() -> new AppException("Survey not found"));
        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AppException("User not found"));
        List<UserQuestionAnswer> answers = userQuestionAnswerRepository.findBySurveyAndUser(survey,user);
        List<AnswerResponse> responces = answers.stream().map(e-> new AnswerResponse(e.getQuestion().getContent(),Optional.ofNullable(e.getTextAnswer()).orElse(e.getChoice().getContent()))).collect(Collectors.toList());
        return ResponseEntity.ok(responces);
    }

    @GetMapping("/getStatRating/{id}")
    public ResponseEntity getStatRating( @PathVariable Long id){
        Survey survey= surveyRepository.findById(id).orElseThrow(() -> new AppException("Survey not found"));
        List<QuestionAverageRating> ratings = userQuestionRatingRepository.countByQuestionGroupBySurveyId(survey.getId());
        return ResponseEntity.ok(ratings);
    }
    @GetMapping("/getStatChoice/{id}")
    public ResponseEntity getStatChoice( @PathVariable Long id){
        Survey survey= surveyRepository.findById(id).orElseThrow(() -> new AppException("Survey not found"));
        List<QuestionChoiceCount> answers = userQuestionAnswerRepository.countByQuestionGroupBySurveyId(survey.getId());
        return ResponseEntity.ok(answers);
    }
    @PostMapping("/submitAnswer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity submitAnswer(@CurrentUser UserPrincipal currentUser,@Valid @RequestBody AnswerBody answers){
        int answerCount = surveyService.submitAnswer(answers.getAnswers(),currentUser.getId());
        return ResponseEntity.ok(new AnswerSubmitResponse("",answerCount,answerCount));
    }

    @GetMapping("/search")
    public ResponseEntity submitAnswer(@RequestParam String searchTitle){

        return ResponseEntity.ok( surveyService.search(searchTitle));
    }

}
