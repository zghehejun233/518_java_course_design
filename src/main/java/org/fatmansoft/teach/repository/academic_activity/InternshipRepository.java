package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    @Query(value = "SELECT max(internshipId) FROM Internship ")
    Integer getMaxId();

    List<Internship> findInternshipsByStudent_StudentId(Integer id);

}
