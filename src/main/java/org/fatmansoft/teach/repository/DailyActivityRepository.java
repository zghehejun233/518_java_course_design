package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DailyActivityRepository extends JpaRepository<DailyActivity,Integer> {
    @Query(value = "select max(id) from DailyActivity ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join DailyActivity d on d.studentName=s.studentName where d.studentName=?1")
    String getStudentNum(String studentName);
}
