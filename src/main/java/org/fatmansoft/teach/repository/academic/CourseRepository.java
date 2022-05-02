package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query(value = "select max(courseId) from Course ")
    Integer getMaxId();


}
