package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.UserQuestionRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionRatingRepository extends JpaRepository<UserQuestionRating, Long> {
}
