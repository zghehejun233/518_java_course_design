package org.fatmansoft.teach.repository.daily;


import org.fatmansoft.teach.models.daily.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost,Integer> {
    @Query(value = "select max(costId) from Cost ")
    public Integer getMaxId();

    List<Cost> findCostsByStudent_StudentId(Integer id);
}
