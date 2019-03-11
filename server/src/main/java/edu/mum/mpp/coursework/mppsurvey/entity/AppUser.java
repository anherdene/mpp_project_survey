package edu.mum.mpp.coursework.mppsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mum.mpp.coursework.mppsurvey.entity.audit.IdDateAudit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@Data
@NoArgsConstructor
public class AppUser extends IdDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Basic
    private long age;
    @Basic
    @Size(max = 15)
    private String username;
    @NotBlank
    @Size(max = 100)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> roles = new HashSet<>();

    public AppUser(@Size(max = 15) String username, @NotBlank @Size(max = 100) String password) {
        this.username = username;
        this.password = password;
    }

    //    @OneToMany(mappedBy="user")
//    private List<UserQuestionAnswer> userQuestionAnswers;
//
//    @OneToMany(mappedBy="user")
//    private List<UserQuestionRating> userQuestionRatings;
//
//    @OneToMany(mappedBy="user")
//    private List<UserSurveyResult> userSurveyResults;
    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(Long id){
         this.id= id;
    }
}
