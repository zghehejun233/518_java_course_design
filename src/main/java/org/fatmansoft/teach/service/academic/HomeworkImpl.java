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

        Map<String, Object> resultMap = new HashMap<>();
        if (homeWork != null) {
            resultMap.put("id", homeWork.getHomeworkId());
            resultMap.put("content", homeWork.getContent());
            resultMap.put("score", homeWork.getScore());
            resultMap.put("courseId", homeWork.getCourse().getName());
            resultMap.put("studentId", homeWork.getStudent().getStudentName());
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

    public Integer insertHomework(HomeWork homeWorkData, Integer courseIdData, Integer studentIdData) {
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
        Optional<Course> optionalCourse = courseRepository.findById(courseIdData);
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
            homeWork.setCourse(course);
        }
        Student student = null;
        Optional<Student> optionalStudent = studentRepository.findById(studentIdData);
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
            homeWork.setStudent(student);
        }
        homeWork.setContent(homeWorkData.getContent());
        homeWork.setScore(homeWorkData.getScore());
        homeWorkRepository.save(homeWork);
        return maxHomeworkId;

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
