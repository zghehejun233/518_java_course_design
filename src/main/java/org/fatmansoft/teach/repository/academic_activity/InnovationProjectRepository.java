package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.InnovationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 16645
 */
@Repository
public interface InnovationProjectRepository extends JpaRepository<InnovationProject, Integer> {
    @Query(value = "SELECT max(innovationProjectId) FROM InnovationProject ")
    Integer getMaxId();

    List<InnovationProject> findInnovationProjectsByStudent_StudentId(Integer id);

}
