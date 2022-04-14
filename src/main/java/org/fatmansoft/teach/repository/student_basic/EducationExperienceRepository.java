package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GuoSurui
 */
public interface EducationExperienceRepository extends JpaRepository<EducationExperience, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "select max(educationExperienceId) from EducationExperience  ")
    Integer getMaxId();
}
