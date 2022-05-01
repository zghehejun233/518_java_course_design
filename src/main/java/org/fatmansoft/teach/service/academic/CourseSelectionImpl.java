package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.Score;
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
            tempMap.put("type", parseType(courseSelection.getType()));
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

        // 如果能找到CourseSelection
        // 则说明是处于查看详情的状态
        // 这个时候只需要直接填充数据即可
        if (courseSelection != null) {
            resultMap.put("courseID", courseSelection.getCourse().getName());
            resultMap.put("studentId", courseSelection.getStudent().getStudentName());
            resultMap.put("id", courseSelection.getCourseSelectionId());
            resultMap.put("type", courseSelection.getType());
        } else {
            // 此时实在建立新的记录
            // 下面首先判断studentId是否存在
            List<Object> studentIdList = new ArrayList<>();
            if (studentId != null) {
                // 存在就开始寻找目标对象
                Student student;
                Optional<Student> optionalStudent = studentRepository.findById(studentId);
                if (optionalStudent.isPresent()) {
                    student = optionalStudent.get();
                    Map map = new HashMap<>();
                    map.put("label", student.getStudentName());
                    map.put("value", student.getStudentId());
                    studentIdList.add(map);
                    resultMap.put("studentId", "");
                    resultMap.put("studentIdList", studentIdList);
                }
            } else {
                // 不存在说明从另一个入口进入，这里开始查找所有的数据
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

    public Integer saveCourseSelection(CourseSelection courseSelectionData,
                                       Integer courseIdData, Integer studentIdData) {
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
        Student student = null;
        Optional<Student> optionalStudent = studentRepository.findById(studentIdData);
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
            courseSelection.setStudent(student);
        }
        Course course = null;
        Optional<Course> optionalCourse = courseRepository.findById(courseIdData);
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
            courseSelection.setCourse(course);
        }

        courseSelection.setCourse(course);
        courseSelection.setStudent(student);
        courseSelectionRepository.save(courseSelection);
        return maxCourseSelectionId;

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

    String parseType(String typeCode) {
        String type;
        switch (typeCode) {
            case "1": {
                type = "必修";
                break;
            }
            case "2": {
                type = "选修";
                break;
            }
            case "3": {
                type = "补修";
                break;
            }
            case "4": {
                type = "辅修";
                break;
            }
            default:
                type = "不明";
        }
        return type;
    }
}
