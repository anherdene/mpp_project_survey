package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.entity.UserQuestionRating;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionAverageRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionRatingRepository extends JpaRepository<UserQuestionRating, Long> {

//    public List<QuestionChoiceCount>

    @Query("SELECT NEW edu.mum.mpp.coursework.mppsurvey.model.QuestionAverageRating(u.question.id, avg (u.rating),count (u.id)) FROM UserQuestionRating u WHERE u.question.survey.id= :surveyId GROUP BY u.question.id")
    List<QuestionAverageRating> countByQuestionGroupBySurveyId(@Param("surveyId") Long surveyId);

    Long countByUser(AppUser user);
}
