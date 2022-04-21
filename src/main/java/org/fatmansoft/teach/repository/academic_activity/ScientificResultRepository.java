package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.ScientificResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScientificResultRepository extends JpaRepository<ScientificResult, Integer> {
    @Query(value = "SELECT max(scientificResultId)  FROM ScientificResult ")
    Integer getMaxId();

    List<ScientificResult> findScientificResultsByStudent_StudentId(Integer id);

}
