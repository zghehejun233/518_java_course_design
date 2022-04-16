package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author GuoSurui
 */
public interface FamilyRepository extends JpaRepository<Family, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "select max(familyId) from Family  ")
    Integer getMaxId();

    List<Family> findFamilyByStudent_StudentId(Integer id);
}
