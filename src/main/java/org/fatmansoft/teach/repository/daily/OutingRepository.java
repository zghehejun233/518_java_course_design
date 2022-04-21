package org.fatmansoft.teach.repository.daily;

import org.fatmansoft.teach.models.daily.Outing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GuoSurui
 */
@Repository
public interface OutingRepository extends JpaRepository<Outing,Integer> {
    @Query(value = "SELECT max(outingId) FROM Outing ")
    Integer getMaxId();

    List<Outing> findOutingsByStudent_StudentId(Integer id);
}
