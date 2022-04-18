package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GuoSurui
 */
@Repository
public interface CourseSelectionRepository extends JpaRepository<CourseSelection,Integer> {

    @Query(value = "select max(courseSelectionId) from CourseSelection  ")
    Integer getMaxId();

    List<CourseSelection> findCourseSelectionsByCourse_CourseId(Integer id);
}
