package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
