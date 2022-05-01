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
        List<Checkout> checkoutList = checkoutRepository
                .findCheckoutsByStudent_StudentIdOrCourse_CourseId(studentId, courseId);
        if (checkoutList.size() == 0) {
            return result;
        }
        Checkout checkout;
        Map<String, Object> tempMap;
        for (Checkout value : checkoutList) {
            checkout = value;
            tempMap = new HashMap<>();
            tempMap.put("id", checkout.getCheckoutId());
            tempMap.put("method", parseMethod(checkout.getMethod()));
            tempMap.put("state", parseState(checkout.getState()));
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

        Map<String, Object> resultMap = new HashMap<>();
        if (checkout != null) {
            resultMap.put("id", checkout.getCheckoutId());
            resultMap.put("method", checkout.getMethod());
            resultMap.put("state", checkout.getState());
            resultMap.put("time", checkout.getTime());
            List studentIdList = new ArrayList<>();
            List<Student> studentList = studentRepository.findAll();
            Map map;
            for (Student value : studentList) {
                map = new HashMap<>();
                map.put("label", value.getStudentName());
                map.put("value", value.getStudentId());
                studentIdList.add(map);
            }
            resultMap.put("studentId", "");
            resultMap.put("studentIdList", studentIdList);

            List courseIdList = new ArrayList();
            List<Course> courseList = courseRepository.findAll();
            for (Course value : courseList) {
                map = new HashMap<>();
                map.put("label", value.getName());
                map.put("value", value.getCourseId());
                courseIdList.add(map);
            }
            resultMap.put("courseId", "");
            resultMap.put("courseIdList", courseIdList);
        } else {
            List studentIdList = new ArrayList<>();
            if (studentId != null) {
                Optional<Student> optionalStudent = studentRepository.findById(studentId);
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    Map map = new HashMap<>();
                    map.put("label", student.getStudentName());
                    map.put("value", student.getStudentId());
                    studentIdList.add(map);
                    resultMap.put("studentId", "");
                    resultMap.put("studentIdList", studentIdList);
                }
            } else {
                List<Student> studentList = studentRepository.findAll();
                Map map;
                for (Student value : studentList) {
                    map = new HashMap<>();
                    map.put("label", value.getStudentName());
                    map.put("value", value.getStudentId());
                    studentIdList.add(map);
                }
                resultMap.put("studentId", "");
                resultMap.put("studentIdList", studentIdList);
            }

            // 以下对课程使用同样的逻辑
            List<Object> courseIdList = new ArrayList<>();
            if (courseId != null) {
                // 存在就开始寻找目标对象
                Course course;
                Optional<Course> optionalCourse = courseRepository.findById(courseId);
                if (optionalCourse.isPresent()) {
                    course = optionalCourse.get();
                    Map map = new HashMap<>();
                    map.put("label", course.getName());
                    map.put("value", course.getCourseId());
                    courseIdList.add(map);
                    resultMap.put("courseId", course.getName());
                    resultMap.put("courseIdList", courseIdList);
                }
            } else {
                // 不存在说明从另一个入口进入，这里开始查找所有的数据
                List<Course> courseList = courseRepository.findAll();
                Map map;
                for (Course value : courseList) {
                    map = new HashMap<>();
                    map.put("label", value.getName());
                    map.put("value", value.getCourseId());
                    courseIdList.add(map);
                }
                resultMap.put("courseId", "");
                resultMap.put("courseIdList", courseIdList);
            }
        }
        return resultMap;
    }

    public Integer insertCheckout(Checkout checkoutData, Integer courseIdData, Integer studentIdData) {
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

        Course course;
        Optional<Course> opCourse = courseRepository.findById(courseIdData);
        if (opCourse.isPresent()) {
            course = opCourse.get();
            checkout.setCourse(course);
        }
        Student student;
        Optional<Student> opStudent = studentRepository.findById(studentIdData);
        if (opStudent.isPresent()) {
            student = opStudent.get();
            checkout.setStudent(student);
        }

        checkout.setMethod(checkoutData.getMethod());
        checkout.setState(checkoutData.getState());
        checkout.setTime(checkoutData.getTime());
        checkoutRepository.save(checkout);
        SystemApplicationListener.logger.info("[Checkout]" + "成功保存考勤信息！");
        return maxCheckoutId;

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

    String parseState(Integer stateCode) {
        String state;
        switch (stateCode) {
            case 1: {
                state = "线下";
                break;
            }
            case 2: {
                state = "线上";
                break;
            }
            case 3: {
                state = "室友代签";
                break;
            }
            default:
                state = "不明";
        }
        return state;
    }

    String parseMethod(String methodCode) {
        String method;
        switch (methodCode) {
            case "1": {
                method = "成了";
                break;
            }
            case "2": {
                method = "没成";
                break;
            }
            default:
                method = "不晓得";
        }
        return method;
    }
}
