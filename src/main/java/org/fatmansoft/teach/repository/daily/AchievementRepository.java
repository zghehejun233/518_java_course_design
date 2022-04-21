package org.fatmansoft.teach.repository.daily;

import org.fatmansoft.teach.models.daily.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    @Query(value = "select max(achievementId) from Achievement")
    Integer getMaxId();

    List<Achievement> findAchievementsByStudent_StudentId(Integer id);

}
