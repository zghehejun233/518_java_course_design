package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
}
