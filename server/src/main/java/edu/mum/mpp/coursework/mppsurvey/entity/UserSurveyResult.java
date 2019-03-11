package edu.mum.mpp.coursework.mppsurvey.entity;

import edu.mum.mpp.coursework.mppsurvey.entity.audit.IdDateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_survey_results")
@Data
public class UserSurveyResult  extends IdDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Basic
    private Date finishDate;
    @Basic
    private Long duration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;
    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
        this.id= id;
    }
}
