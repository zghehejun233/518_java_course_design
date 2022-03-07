package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Id;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query(value = "select max(id) from Score ")
    Integer getMaxId();
}
