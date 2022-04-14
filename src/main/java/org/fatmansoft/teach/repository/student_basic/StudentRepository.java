package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "select max(studentId) from Student  ")
    Integer getMaxId();

    @Query(value = "from Student where ?1='' or studentNum like %?1% or studentName like %?1% ")
    List<Student> findStudentListByNumName(String numName);


}
