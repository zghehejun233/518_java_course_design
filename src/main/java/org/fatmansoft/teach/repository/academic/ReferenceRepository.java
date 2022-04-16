package org.fatmansoft.teach.repository.academic;


import org.fatmansoft.teach.models.academic.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
    @Query(value = "select max(id) from Reference ")
    Integer getMaxId();
}
