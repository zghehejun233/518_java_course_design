package org.fatmansoft.teach.repository.academic;


import org.fatmansoft.teach.models.academic.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeWorkRepository extends JpaRepository<HomeWork,Integer> {
    @Query(value = "select max(id) from HomeWork ")
    Integer getMaxId();
}
