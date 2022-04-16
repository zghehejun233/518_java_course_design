package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author GuoSurui
 */
public interface SocialRelationRepository extends JpaRepository<SocialRelation, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "SELECT MAX(socialRelationId) FROM SocialRelation")
    Integer getMaxId();
    List<SocialRelation> findSocialRelationsByStudent_StudentId(Integer id);
}
