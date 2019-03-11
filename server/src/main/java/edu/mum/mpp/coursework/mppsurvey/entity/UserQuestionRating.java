package edu.mum.mpp.coursework.mppsurvey.entity;

import edu.mum.mpp.coursework.mppsurvey.entity.audit.IdDateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_question_ratings")
@Data
public class UserQuestionRating extends IdDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Basic
    private  Long rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
        this.id= id;
    }
}
