package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GuoSurui
 */
public interface CourseSelectionRepository extends JpaRepository<CourseSelection,Integer> {
    @Query(value = "select max(id) from CourseSelection ")
    Integer getMaxId();
}
