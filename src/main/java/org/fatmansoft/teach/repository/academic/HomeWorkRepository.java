package org.fatmansoft.teach.repository.academic;


import org.fatmansoft.teach.models.academic.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeWorkRepository extends JpaRepository<HomeWork,Integer> {
    @Query(value = "select max(homeworkId) from HomeWork ")
    Integer getMaxId();

    List<HomeWork> findAllByStudent_StudentId(Integer id);
}
