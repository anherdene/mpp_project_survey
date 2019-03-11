package edu.mum.mpp.coursework.mppsurvey.controller.admin;


import edu.mum.mpp.coursework.mppsurvey.entity.Choice;
import edu.mum.mpp.coursework.mppsurvey.entity.Question;
import edu.mum.mpp.coursework.mppsurvey.entity.Survey;
import edu.mum.mpp.coursework.mppsurvey.exception.ResourceNotFoundException;
import edu.mum.mpp.coursework.mppsurvey.model.ChoiceRequest;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionRequest;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/surveys")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSurveyController extends RestCrudController<Survey> {
    private static final Logger logger = LoggerFactory.getLogger(AdminSurveyController.class);

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/{id}/addQuestion")
    public ResponseEntity addQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequest questionRequest) {
//        Optional<Survey> optional = repository.findById(id);
        logger.info(questionRequest.toString());
        Survey survey = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey", "id", id));

//        survey.addQuestion();

        Question question = new Question();

        question.setType(QuestionType.valueOf(questionRequest.getType()));
        question.setContent(questionRequest.getContent());
        question.setOptional(questionRequest.isOptional());
        survey.addQuestion(question);
        logger.info(survey.toString());
        logger.info(question.toString());
        for(ChoiceRequest choiceRequest:questionRequest.getChoices()){
            Choice choice = new Choice();
            choice.setDefault(choiceRequest.isDefault());
            choice.setContent(choiceRequest.getContent());
            question.addChoice(choice);
        }

        repository.save(survey);

        return ResponseEntity.ok(survey);
    }

}
