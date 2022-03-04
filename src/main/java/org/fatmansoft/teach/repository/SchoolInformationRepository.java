package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.SchoolInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchoolInformationRepository extends JpaRepository<SchoolInformation, Integer> {
    @Query(value = "select max(id) from SchoolInformation  ")
    Integer getMaxId();

    @Query(value = "select s.studentNum from Student s inner join SchoolInformation h on s.studentName=h.studentName where h.studentName=?1")
    String getStudentNum(String studentName);
}
