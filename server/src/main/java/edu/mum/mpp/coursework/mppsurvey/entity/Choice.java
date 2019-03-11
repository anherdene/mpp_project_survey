package edu.mum.mpp.coursework.mppsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.mum.mpp.coursework.mppsurvey.entity.audit.UserDateAudit;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "choices")
@Data
@ToString(exclude = {"question"})
@JsonIgnoreProperties(
        value = {"question","createdAt", "updatedAt","createdBy", "updatedBy"}
)
public class Choice extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @NotBlank
    @Size(max = 200)
    private  String content;

    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(id, choice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
        this.id= id;
    }
}
