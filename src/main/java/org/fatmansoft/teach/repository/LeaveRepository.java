package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.daily.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaveRepository extends JpaRepository<Leave,Integer> {
    @Query(value = "select max(id) from Leave ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join Leave l on l.studentName=s.studentName where l.studentName=?1")
    String getStudentNum(String studentName);
}
