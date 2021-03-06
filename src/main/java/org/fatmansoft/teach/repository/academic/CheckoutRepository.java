package org.fatmansoft.teach.repository.academic;

import org.fatmansoft.teach.models.academic.Checkout;
import org.fatmansoft.teach.models.academic.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GuoSurui
 */
@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
    @Query(value = "SELECT max(checkoutId) FROM Checkout ")
    Integer getMaxId();

    List<Checkout> findCheckoutsByStudent_StudentIdOrCourse_CourseId(Integer studentId,Integer courseId);


}
