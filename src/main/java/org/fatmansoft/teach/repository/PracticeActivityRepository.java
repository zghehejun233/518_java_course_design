package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.PracticeActivity;
import org.fatmansoft.teach.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PracticeActivityRepository extends JpaRepository<PracticeActivity,Integer> {
    @Query(value = "select max(id) from PracticeActivity ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join PracticeActivity p on s.studentName=p.studentName where p.studentName=?1")
    String getStudentNum(String studentName);
}
