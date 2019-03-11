package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
