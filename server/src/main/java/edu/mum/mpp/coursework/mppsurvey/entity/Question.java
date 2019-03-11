package edu.mum.mpp.coursework.mppsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.mum.mpp.coursework.mppsurvey.entity.audit.UserDateAudit;
import edu.mum.mpp.coursework.mppsurvey.model.QuestionType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
@Data
@ToString(exclude = {"choices","survey"})
@JsonIgnoreProperties(
        value = {"survey","createdDate", "updatedDate"}
)
public class Question extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private QuestionType type;

//    @NotBlank
    @Size(max = 200)
    private  String content;

    private  boolean isOptional;

    @OneToMany(mappedBy="question",cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Choice> choices;

    @ManyToOne
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;

    public Question() {
        choices = new ArrayList<>();
    }

    public void addChoice(Choice choice){
        choice.setQuestion(this);
        choices.add(choice);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setQuestion(null);
    }
    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
        this.id= id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question choice = (Question) o;
        return Objects.equals(id, choice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
