package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.Survey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

//    @Query("SELECT NEW edu.mum.mpp.coursework.mppsurvey.model.SurveySummary(s.id, s.title,s.description,10) FROM Survey s order by s.id desc ")
////    List<SurveySummary> findSurveyBrief(Pageable pageable);
    List<Survey> findAllByOrderByIdDesc(Pageable pageable);

    List<Survey> findAllByTitleIsLikeOrderByIdDesc(String title,Pageable pageable);
}
