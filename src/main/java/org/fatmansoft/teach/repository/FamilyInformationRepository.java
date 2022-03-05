package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.FamilyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FamilyInformationRepository extends JpaRepository<FamilyInformation, Integer> {
    @Query(value = "select max(id) from FamilyInformation  ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join FamilyInformation f on s.studentName = f.studentName where f.studentName=?1")
    String getStudentNum(String studentName);
}