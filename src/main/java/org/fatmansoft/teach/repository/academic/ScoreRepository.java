package org.fatmansoft.teach.repository.academic;


import org.fatmansoft.teach.models.academic.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query(value = "select max(id) from Score ")
    Integer getMaxId();
}
