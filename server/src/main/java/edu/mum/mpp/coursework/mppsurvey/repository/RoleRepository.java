package edu.mum.mpp.coursework.mppsurvey.repository;

import edu.mum.mpp.coursework.mppsurvey.entity.AppRole;
import edu.mum.mpp.coursework.mppsurvey.model.AppRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findByName(AppRoleName name);
}
