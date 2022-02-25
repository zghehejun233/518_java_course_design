package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Achievement;
import org.fatmansoft.teach.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    @Query(value = "from Achievement where ?1='' or studentNum like %?1% or studentName like %?1% ")
    List<Achievement> findAchievementListByNumName(String numName);

}
