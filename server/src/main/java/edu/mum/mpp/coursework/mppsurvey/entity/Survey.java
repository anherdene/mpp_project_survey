package edu.mum.mpp.coursework.mppsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.mum.mpp.coursework.mppsurvey.entity.audit.UserDateAudit;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "surveys")
@Data
@ToString(exclude = {"questions"})
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer","createdDate", "updatedDate"})
public class Survey extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @NotBlank
    @Size(max = 100)
    private  String title;

    @NotBlank
    @Size(max = 200)
    private  String description;

    @Basic
    private Date openDate;

    @Basic
    private Date closeDate;

    @OneToMany(mappedBy="survey",cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Question> questions;


    public Survey() {
        questions = new ArrayList<>();
    }
    public int getQuestionSize(){
        return questions.size();
    }
    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
        this.id= id;
    }

    public void addQuestion(Question question){
        question.setSurvey(this);
        questions.add(question);
    }


}
