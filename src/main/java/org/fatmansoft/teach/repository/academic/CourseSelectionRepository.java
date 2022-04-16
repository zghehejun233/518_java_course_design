package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GuoSurui
 */
public interface CourseSelectionRepository extends JpaRepository<CourseSelection,Integer> {

}
