package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GuoSurui
 */
public interface SocialRelationRepository extends JpaRepository<SocialRelation, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "select max(socialRelationId) from SocialRelation  ")
    Integer getMaxId();
}
