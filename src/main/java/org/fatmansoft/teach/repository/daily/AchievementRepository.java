package org.fatmansoft.teach.repository.daily;

import org.fatmansoft.teach.models.daily.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    @Query(value = "select max(achievementId) from Achievement")
    Integer getMaxId();

}
