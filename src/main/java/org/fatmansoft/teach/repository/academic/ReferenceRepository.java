package org.fatmansoft.teach.repository.academic;


import org.fatmansoft.teach.models.academic.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
    @Query(value = "select max(referenceId) from Reference ")
    Integer getMaxId();

    List<Reference> findReferencesByCourse_CourseId(Integer id);
}
