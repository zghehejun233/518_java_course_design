package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GuoSurui
 */
public interface CourseSelectionRepository extends JpaRepository<CourseSelection,Integer> {
    @Query(value = "select max(id) from CourseSelection ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join CourseSelection c on s.studentName=c.studentName where c.studentName=?1")
    String getStudentNum(String studentName);

    @Query(value = "select c.courseNum from Course c inner join CourseSelection cs on c.courseName=cs.courseName where cs.courseName=?1")
    String getCourseNum(String courseName);
}
