package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.academic_activity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PracticeActivityRepository extends JpaRepository<Practice,Integer> {
}
