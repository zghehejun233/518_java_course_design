package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.CourseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GuoSurui
 */
@Repository
public interface CourseTimeRepository extends JpaRepository<CourseTime, Integer> {
    @Query(value = "select max(courseTimeId) from CourseTime")
    Integer getMaxId();

}
