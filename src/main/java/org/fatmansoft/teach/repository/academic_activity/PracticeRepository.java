package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<Practice,Integer> {
    @Query(value = "SELECT max(practiceId) FROM Practice ")
    Integer getMaxId();

    List<Practice> findPracticesByStudent_StudentId(Integer id);
}
