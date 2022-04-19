package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SpringBootSecurityJwtApplication;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Checkout;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.repository.academic.CheckoutRepository;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class CheckoutImpl {
    @Resource
    private CheckoutRepository checkoutRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer courseId;
    private Integer studentId;

    public List<Object> queryAllCheckout() {
        List<Object> result = new ArrayList<>();
        List<Checkout> checkoutList = checkoutRepository.findCheckoutsByCourse_CourseId(courseId);
        if (checkoutList.size() == 0) {
            return result;
        }
        Checkout checkout;
        Map<String, Object> tempMap;
        for (Checkout value : checkoutList) {
            checkout = value;
            tempMap = new HashMap<>();
            tempMap.put("id", checkout.getCheckoutId());
            tempMap.put("method", checkout.getMethod());
            tempMap.put("state", checkout.getState());
            tempMap.put("time", checkout.getTime());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryCheckoutDetail(Integer checkoutId) {
        Checkout checkout = null;
        Optional<Checkout> op;
        if (checkoutId != null) {
            op = checkoutRepository.findById(checkoutId);
            if (op.isPresent()) {
                checkout = op.get();
            }
        }

        Map<String, Object> ressultMap = new HashMap<>();
        if (checkout != null) {
            ressultMap.put("id", checkout.getCheckoutId());
            ressultMap.put("method", checkout.getMethod());
            ressultMap.put("state", checkout.getState());
            ressultMap.put("time", checkout.getTime());
        }
        return ressultMap;
    }

    public Integer insertCheckout(Checkout checkoutData, String courseName, String studentName) {
        Checkout checkout = null;
        Optional<Checkout> op;
        if (checkoutData.getCheckoutId() != null) {
            op = checkoutRepository.findById(checkoutData.getCheckoutId());
            if (op.isPresent()) {
                checkout = op.get();
            }
        }

        Integer maxCheckoutId = null;
        if (checkout == null) {
            checkout = new Checkout();
            maxCheckoutId = checkoutRepository.getMaxId();
            if (maxCheckoutId == null) {
                maxCheckoutId = 1;
            } else {
                maxCheckoutId += 1;
            }
            checkout.setCheckoutId(maxCheckoutId);
        }

        Course course = null;
        Optional<Course> opCourse;
        Student student = null;
        Optional<Student> opStudent;
        if ((courseId == null && courseName != null)) {
            course = courseRepository.findFirstByNameOrNum(courseName, courseName);
            SystemApplicationListener.logger.info("[Checkout]" + "找到关联的课程信息");

        } else {
            try {
                opCourse = courseRepository.findById(courseId);
                if (opCourse.isPresent()) {
                    course = opCourse.get();
                    SystemApplicationListener.logger.info("[Checkout]" + "找到关联的课程信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Checkout]" + "没有找到关联的课程信息");
                return 1;
            }
        }
        if (studentId == null && studentName != null) {
            student = studentRepository.findFirstByStudentNameOrStudentNum(studentName, studentName);
            SystemApplicationListener.logger.info("[Checkout]" + "找到关联的学生信息");

        } else {
            try {
                opStudent = studentRepository.findById(studentId);
                if (opStudent.isPresent()) {
                    student = opStudent.get();
                    SystemApplicationListener.logger.info("[Checkout]" + "找到关联的学生信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Checkout]" + "没有找到关联的学生信息");
                return 1;
            }
        }

        if (course == null || student == null) {
            SystemApplicationListener.logger.error("[Checkout]" + "完蛋了，都没有");
            return 1;
        } else {
            checkout.setCourse(course);
            checkout.setStudent(student);
            checkoutRepository.save(checkout);
            SystemApplicationListener.logger.info("[Checkout]" + "成功保存考勤信息！");
            return maxCheckoutId;
        }
    }

    public void dropCheckout(Integer checkoutId) {
        Checkout checkout;
        Optional<Checkout> op;
        if (checkoutId != null) {
            op = checkoutRepository.findById(checkoutId);
            if (op.isPresent()) {
                checkout = op.get();
                SystemApplicationListener.logger.info("[Checkout]: 找到要删除的考勤记录" + checkout);

                Course relatedCourse;
                Optional<Course> opCourse = courseRepository.findById(checkout.getCourse().getCourseId());
                if (opCourse.isPresent()) {
                    relatedCourse = opCourse.get();
                    relatedCourse.getCheckouts().remove(checkout);
                    SystemApplicationListener.logger.info("[Checkout]:找到关联的课程信息" + relatedCourse);

                }

                Student relatedStudent;
                Optional<Student> opStudent = studentRepository.findById(checkout.getStudent().getStudentId());
                if (opStudent.isPresent()) {
                    relatedStudent = opStudent.get();
                    relatedStudent.getCheckouts().remove(checkout);
                    SystemApplicationListener.logger.info("[Checkout]:找到关联的学生信息" + relatedStudent);
                }
                checkoutRepository.delete(checkout);
                SystemApplicationListener.logger.info("[Checkout]:删除考勤信息记录成功！");
            }
        }
    }
}
