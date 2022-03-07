package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query(value = "select max(id) from Attendance ")
    Integer getMaxId();

}
