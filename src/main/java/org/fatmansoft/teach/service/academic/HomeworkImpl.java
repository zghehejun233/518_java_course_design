package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Checkout;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.HomeWork;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.HomeWorkRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class HomeworkImpl {
    @Resource
    private HomeWorkRepository homeWorkRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer courseId;
    private Integer studentId;

    public List<Object> queryAllHomework() {
        List<Object> result = new ArrayList<>();
        List<HomeWork> homeWorkList = homeWorkRepository.findAllByStudent_StudentId(studentId);
        if (homeWorkList.size() == 0) {
            return result;
        }
        HomeWork homeWork;
        Map<String, Object> tempMap;
        for (HomeWork value : homeWorkList) {
            homeWork = value;
            tempMap = new HashMap<>();
            tempMap.put("id", homeWork.getHomeworkId());
            tempMap.put("content", homeWork.getContent());
            tempMap.put("score", homeWork.getScore());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryHomeworkDetail(Integer homeworkId) {
        HomeWork homeWork = null;
        Optional<HomeWork> op;
        if (homeworkId != null) {
            op = homeWorkRepository.findById(homeworkId);
            if (op.isPresent()) {
                homeWork = op.get();
            }
        }

        Map<String, Object> ressultMap = new HashMap<>();
        if (homeWork != null) {
            ressultMap.put("id", homeWork.getHomeworkId());
            ressultMap.put("content", homeWork.getContent());
            ressultMap.put("score", homeWork.getScore());
        }
        return ressultMap;
    }

    public Integer insertHomework(HomeWork homeWorkData, String courseName, String studentName) {
        HomeWork homeWork = null;
        Optional<HomeWork> op;
        if (homeWorkData.getHomeworkId() != null) {
            op = homeWorkRepository.findById(homeWorkData.getHomeworkId());
            if (op.isPresent()) {
                homeWork = op.get();
            }
        }

        Integer maxHomeworkId = null;
        if (homeWork == null) {
            homeWork = new HomeWork();
            maxHomeworkId = homeWorkRepository.getMaxId();
            if (maxHomeworkId == null) {
                maxHomeworkId = 1;
            } else {
                maxHomeworkId += 1;
            }
            homeWork.setHomeworkId(maxHomeworkId);
        }

        Course course = null;
        Optional<Course> opCourse;
        Student student = null;
        Optional<Student> opStudent;
        if ((courseId == null && courseName != null)) {
            course = courseRepository.findFirstByNameOrNum(courseName, courseName);
            SystemApplicationListener.logger.info("[Homework]" + "找到关联的课程信息");
        } else {
            try {
                opCourse = courseRepository.findById(courseId);
                if (opCourse.isPresent()) {
                    course = opCourse.get();
                    SystemApplicationListener.logger.info("[Homework]" + "找到关联的课程信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Homework]" + "没有找到关联的课程信息");
                return 1;
            }
        }
        if (studentId == null && studentName != null) {
            student = studentRepository.findFirstByStudentNameOrStudentNum(studentName, studentName);
            SystemApplicationListener.logger.info("[Homework]" + "找到关联的学生信息");

        } else {
            try {
                opStudent = studentRepository.findById(studentId);
                if (opStudent.isPresent()) {
                    student = opStudent.get();
                    SystemApplicationListener.logger.info("[Homework]" + "找到关联的学生信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Homework]" + "没有找到关联的学生信息");
                return 1;
            }
        }
        if (course == null || student == null) {
            SystemApplicationListener.logger.error("[Homework]" + "完蛋了，都没有");
            return 1;
        } else {
            homeWork.setCourse(course);
            homeWork.setStudent(student);
            homeWorkRepository.save(homeWork);
            SystemApplicationListener.logger.info("[Homework]" + "成功保存作业信息！");
            return maxHomeworkId;
        }
    }

    public void dropHomework(Integer homeworkId) {
        HomeWork homeWork;
        Optional<HomeWork> op;
        if (homeworkId != null) {
            op = homeWorkRepository.findById(homeworkId);
            if (op.isPresent()) {
                homeWork = op.get();
                SystemApplicationListener.logger.info("[Homework]: 找到要删除的作业记录" + homeWork);

                Course relatedCourse;
                Optional<Course> opCourse = courseRepository.findById(homeWork.getCourse().getCourseId());
                if (opCourse.isPresent()) {
                    relatedCourse = opCourse.get();
                    relatedCourse.getCheckouts().remove(homeWork);
                    SystemApplicationListener.logger.info("[Homework]:找到关联的作业信息" + relatedCourse);

                }

                Student relatedStudent;
                Optional<Student> opStudent = studentRepository.findById(homeWork.getStudent().getStudentId());
                if (opStudent.isPresent()) {
                    relatedStudent = opStudent.get();
                    relatedStudent.getCheckouts().remove(homeWork);
                    SystemApplicationListener.logger.info("[Homework]:找到关联的作业信息" + relatedStudent);
                }
                homeWorkRepository.delete(homeWork);
                SystemApplicationListener.logger.info("[Homework]:删除作业信息记录成功！");
            }
        }
    }
}
