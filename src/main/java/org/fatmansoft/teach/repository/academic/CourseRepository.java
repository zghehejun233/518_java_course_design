package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query(value = "select max(id) from Course ")
    Integer getMaxId();

}
