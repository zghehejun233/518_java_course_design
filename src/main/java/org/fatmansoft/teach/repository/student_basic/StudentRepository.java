package org.fatmansoft.teach.repository.student_basic;

import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    /**
     * 获取最大id
     *
     * @return 最大id
     */
    @Query(value = "select max(studentId) from Student  ")
    Integer getMaxId();

    /**
     * 根据学号或姓名查询对象
     *
     * @param numName 学号或者姓名
     * @return 对象数组
     */
    @Query(value = "from Student where ?1='' or studentNum like %?1% or studentName like %?1% ")
    List<Student> findStudentListByNumName(String numName);

}
