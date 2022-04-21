package org.fatmansoft.teach.repository.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Integer> {
    @Query(value = "SELECT max(lectureId) FROM Lecture ")
    Integer getMaxId();

    List<Lecture> findLecturesByStudent_StudentId(Integer id);
}
