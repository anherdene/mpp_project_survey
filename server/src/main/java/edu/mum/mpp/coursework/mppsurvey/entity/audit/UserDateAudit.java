package edu.mum.mpp.coursework.mppsurvey.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;


@Data
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"}
)
public abstract class UserDateAudit extends IdDateAudit {

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;
}
