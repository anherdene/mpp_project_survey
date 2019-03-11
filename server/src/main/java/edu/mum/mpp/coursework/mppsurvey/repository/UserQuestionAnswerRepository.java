package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.entity.Survey;
import edu.mum.mpp.coursework.mppsurvey.entity.UserQuestionAnswer;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionChoiceCount;
import edu.mum.mpp.coursework.mppsurvey.model.UserSurveyCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionAnswerRepository extends JpaRepository<UserQuestionAnswer, Long> {

    @Query("SELECT NEW edu.mum.mpp.coursework.mppsurvey.model.QuestionChoiceCount(u.question.id,u.choice.id, count(u.id)) FROM UserQuestionAnswer u WHERE u.survey.id = :surveyId GROUP BY u.question.id,u.choice.id")
    List<QuestionChoiceCount> countByQuestionGroupBySurveyId(@Param("surveyId") Long surveyId);


    @Query("SELECT NEW edu.mum.mpp.coursework.mppsurvey.model.UserSurveyCount(u.user.id,count(u.id)) FROM UserQuestionAnswer u GROUP BY u.user.id")
    List<UserSurveyCount> countByUserGroup();

    Long countByUser(AppUser user);

    List<UserQuestionAnswer> findBySurveyAndUser(Survey survey, AppUser user);
}
