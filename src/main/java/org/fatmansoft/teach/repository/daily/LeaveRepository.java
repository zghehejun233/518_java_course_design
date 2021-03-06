package org.fatmansoft.teach.repository.daily;

import org.fatmansoft.teach.models.daily.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Integer> {
    @Query(value = "select max(leaveId) from Leave ")
    Integer getMaxId();

    List<Leave> findLeavesByStudent_StudentId(Integer id);

}
