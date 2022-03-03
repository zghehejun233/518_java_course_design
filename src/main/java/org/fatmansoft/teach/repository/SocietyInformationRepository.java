package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.SocietyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocietyInformationRepository extends JpaRepository<SocietyInformation, Integer> {
    @Query(value = "select max(id) from SocietyInformation  ")
    Integer getMaxId();
}
