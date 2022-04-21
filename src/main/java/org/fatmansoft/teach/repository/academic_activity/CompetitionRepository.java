package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Integer> {
    @Query(value = "SELECT max(competitionId) FROM Competition ")
    Integer getMaxId();

    List<Competition> findCompetitionsByStudent_StudentId(Integer id);
}
