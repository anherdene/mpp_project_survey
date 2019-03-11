package edu.mum.mpp.coursework.mppsurvey.service;

import edu.mum.mpp.coursework.mppsurvey.entity.*;
import edu.mum.mpp.coursework.mppsurvey.model.AnswerRequest;
import edu.mum.mpp.coursework.mppsurvey.model.BatchModel;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionType;
import edu.mum.mpp.coursework.mppsurvey.model.SurveySummary;
import edu.mum.mpp.coursework.mppsurvey.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private static final Logger logger = LoggerFactory.getLogger(SurveyService.class);

    @Autowired
    private UserQuestionRatingRepository userQuestionRatingRepository;
    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    /**
     *
     * @param searchTitle
     * @return
     */
    public List<SurveySummary> search(String searchTitle){
        // like %searchTitle%
       return surveyRepository.findAllByTitleIsLikeOrderByIdDesc("%"+searchTitle+"%", PageRequest.of(0,30)).stream().map(SurveySummary::newInstance).collect(Collectors.toList());
    }

    /**
     * load from batch file
     * @param batchModels
     */
    public void loadFromBatch(List<BatchModel> batchModels){
        Survey survey = new Survey();
        survey.setTitle("test");
        survey.setDescription("dfesc");
        for(BatchModel batchModel:batchModels){
            Question question = new Question();
            question.setContent(batchModel.getQuestion());

            if(batchModel.getQType()!=null){
                question.setType(QuestionType.valueOf(batchModel.getQType().toUpperCase()));
            }

            if(question.getType()==QuestionType.MC){
                // add choice
                question.addChoice(createChoice(batchModel.getAnswer1(),"1".equals(batchModel.getIsDefault())));
                question.addChoice(createChoice(batchModel.getAnswer2(),"2".equals(batchModel.getIsDefault())));
                question.addChoice(createChoice(batchModel.getAnswer3(),"3".equals(batchModel.getIsDefault())));
                question.addChoice(createChoice(batchModel.getAnswer4(),"4".equals(batchModel.getIsDefault())));
            }
            survey.addQuestion(question);
        }

        surveyRepository.save(survey);
    }

    /**
     *
     * @param choiceText
     * @param isDefault
     * @return Choice
     */
    private Choice createChoice(String choiceText,boolean isDefault){
        Choice choice = new Choice();
        logger.info(choiceText);
        choice.setContent(choiceText);
        choice.setDefault(isDefault);
        return choice;
    }

    /**
     * rate question till 1-5 star
     * @param questionId
     * @param userId
     * @param rate
     * @return
     */
    public UserQuestionRating rateQuestion(Long questionId, Long userId, Long rate) {
        Optional<Question> optQuestion = questionRepository.findById(questionId);
        Optional<AppUser> optUser = userRepository.findById(userId);

        if(optQuestion.isPresent() && optUser.isPresent()){
            UserQuestionRating userQuestionRating = new UserQuestionRating();
            userQuestionRating.setRating(rate);
            userQuestionRating.setQuestion(optQuestion.get());
            userQuestionRating.setUser(optUser.get());
            return   userQuestionRatingRepository.save(userQuestionRating);
        }
        return null;
    }

    /**
     * save submitted answers
     * @param answers
     * @param userId
     * @return
     */
    public int submitAnswer(List<AnswerRequest> answers, Long userId){

        Optional<AppUser> optUser = userRepository.findById(userId);
        List<UserQuestionAnswer> questionAnswers = new ArrayList<>();
        for(AnswerRequest answer:answers) {
            Optional<Question> optQuestion = questionRepository.findById(answer.getQuestionId());
            Optional<Choice> optChoice = choiceRepository.findById(answer.getChoiceId()!=null?answer.getChoiceId():0);
            Optional<Survey> optSurvey = surveyRepository.findById(answer.getSurveyId());
            if (optQuestion.isPresent() && optUser.isPresent() && optSurvey.isPresent()) {
                UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer();
                userQuestionAnswer.setTextAnswer(answer.getTextAnswer());
                if(optChoice.isPresent()) {
                    userQuestionAnswer.setChoice(optChoice.get());
                }
                userQuestionAnswer.setQuestion(optQuestion.get());
                userQuestionAnswer.setUser(optUser.get());
                userQuestionAnswer.setSurvey(optSurvey.get());
                userQuestionAnswerRepository.save(userQuestionAnswer);
            }
        }
//        questionAnswers = userQuestionAnswerRepository.saveAll(questionAnswers);
        return questionAnswers.size() ;
    }

}
