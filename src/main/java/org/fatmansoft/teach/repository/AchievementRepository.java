package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    @Query(value = "select max(id) from Achievement")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join Achievement a " +
            "on s.studentName = a.studentName where a.studentName=?1")
    String getStudentNum(String studentName);
}
