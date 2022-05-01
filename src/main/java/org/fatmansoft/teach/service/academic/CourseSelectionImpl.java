package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.CourseSelectionRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class CourseSelectionImpl {
    @Resource
    private CourseSelectionRepository courseSelectionRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StudentRepository studentRepository;

    /**
     * 课程id
     * 学生id
     *
     */
    private Integer courseId = null;
    private Integer studentId = null;

    public List<Object> getCourseSelectionMapList() {
        List<Object> result = new ArrayList<>();
        List<CourseSelection> courseSelectionList = courseSelectionRepository
                .findCourseSelectionsByCourse_CourseIdOrStudent_StudentId(courseId, studentId);
        if (courseSelectionList.size() == 0) {
            return result;
        }
        CourseSelection courseSelection;
        Map<String, Object> tempMap;
        for (CourseSelection value : courseSelectionList) {
            courseSelection = value;
            tempMap = new HashMap<>();
            tempMap.put("id", courseSelection.getCourseSelectionId());
            tempMap.put("courseName", courseSelection.getCourse().getName());
            tempMap.put("courseNum", courseSelection.getCourse().getNum());
            tempMap.put("studentName", courseSelection.getStudent().getStudentName());
            tempMap.put("type", courseSelection.getType());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> getCourseSelectionDetail(Integer id) {
        CourseSelection courseSelection = null;
        Optional<CourseSelection> op;
        if (id != null) {
            op = courseSelectionRepository.findById(id);
            if (op.isPresent()) {
                courseSelection = op.get();
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        if (courseSelection != null) {
            resultMap.put("id", courseSelection.getCourseSelectionId());
            resultMap.put("course", courseSelection.getCourse().getName());
            resultMap.put("student", courseSelection.getStudent().getStudentName());
            resultMap.put("type", courseSelection.getType());
        }
        return resultMap;
    }

    public Integer saveCourseSelection(CourseSelection courseSelectionData,
                                       String courseName, String studentName) {
        // 尝试获取选课记录的实例
        CourseSelection courseSelection = null;
        Optional<CourseSelection> op;
        if (courseSelectionData.getCourseSelectionId() != null) {
            op = courseSelectionRepository.findById(courseSelectionData.getCourseSelectionId());
            if (op.isPresent()) {
                courseSelection = op.get();
            }
        }

        // 查找最大id
        Integer maxCourseSelectionId = null;
        if (courseSelection == null) {
            courseSelection = new CourseSelection();
            maxCourseSelectionId = courseSelectionRepository.getMaxId();
            if (maxCourseSelectionId == null) {
                maxCourseSelectionId = 1;
            } else {
                maxCourseSelectionId += 1;
            }
            // 设置主键
            courseSelection.setCourseSelectionId(maxCourseSelectionId);
        }

        //  寻找相关的Course和Student
        Course course = null;
        Optional<Course> opCourse;
        Student student = null;
        Optional<Student> opStudent;
        // 查找课程
        if ((courseId == null && courseName != null)) {
            course = courseRepository.findFirstByName(courseName);
            SystemApplicationListener.logger.info("[CourseSelection]" + "找到关联的课程信息");
        } else {
            try {
                opCourse = courseRepository.findById(courseId);
                if (opCourse.isPresent()) {
                    course = opCourse.get();
                    SystemApplicationListener.logger.info("[CourseSelection]" + "找到关联的课程信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[CourseSelection]" + "没有找到关联的课程信息");
                return 1;
            }
        }
        // 查找student
        if (studentId == null && studentName != null) {
            student = studentRepository.findFirstByStudentNameOrStudentNum(studentName, studentName);
            SystemApplicationListener.logger.info("[CourseSelection]" + "找到关联的学生信息");
        } else {
            try {
                opStudent = studentRepository.findById(studentId);
                if (opStudent.isPresent()) {
                    student = opStudent.get();
                    SystemApplicationListener.logger.info("[CourseSelection]" + "找到关联的学生信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[CourseSelection]" + "没有找到关联的学生信息");
                return 1;
            }
        }
        if (course == null || student == null) {
            SystemApplicationListener.logger.error("[CourseSelection]" + "完蛋了，都没有");
            return 1;
        } else {
            courseSelection.setCourse(course);
            courseSelection.setStudent(student);
            courseSelectionRepository.save(courseSelection);
            SystemApplicationListener.logger.info("[CourseSelection]" + "成功保存选课信息！");
            return maxCourseSelectionId;
        }
    }

    public void deleteCourseSelection(Integer courseSelectionId) {
        CourseSelection courseSelection;
        Optional<CourseSelection> op;
        if (courseSelectionId != null) {
            op = courseSelectionRepository.findById(courseSelectionId);
            if (op.isPresent()) {
                courseSelection = op.get();
                SystemApplicationListener.logger.info(courseSelection.toString());

                Course relatedCourse;
                Optional<Course> opCourse = courseRepository.findById(courseSelection.getCourse().getCourseId());
                if (opCourse.isPresent()) {
                    relatedCourse = opCourse.get();
                    relatedCourse.getCourseSelections().remove(courseSelection);
                    SystemApplicationListener.logger.error(relatedCourse.getCourseSelections().toString());
                }

                Student relatedStudent;
                Optional<Student> opStudent = studentRepository.findById(courseSelection.getStudent().getStudentId());
                if (opStudent.isPresent()) {
                    relatedStudent = opStudent.get();
                    relatedStudent.getCourseSelections().remove(courseSelection);
                    SystemApplicationListener.logger.error(relatedStudent.getCourseSelections().toString());
                }
                courseSelectionRepository.delete(courseSelection);
            }
        }
    }
}
