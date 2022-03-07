package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;

public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
    @Query(value = "select max(id) from Reference ")
    Integer getMaxId();
}
