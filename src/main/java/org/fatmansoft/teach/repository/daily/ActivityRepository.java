package org.fatmansoft.teach.repository.daily;

import org.fatmansoft.teach.models.daily.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    @Query(value = "SELECT max(activityId) FROM Activity ")
    Integer getMaxId();

    List<Activity> findActivitiesByStudent_StudentId(Integer id);
}
