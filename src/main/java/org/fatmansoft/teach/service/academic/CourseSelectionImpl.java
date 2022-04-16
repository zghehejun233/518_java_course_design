package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.CourseSelectionRepository;
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

    private Integer courseId;
    private Integer studentId;

    public List<Object> getCourseSelectionMapList() {
        List<Object> result = new ArrayList<>();
        List<CourseSelection> courseSelectionList = courseSelectionRepository
                .findCourseSelectionsByCourse_CourseIdOrCourse_Name(courseId);
        if (courseSelectionList.size() == 0) {
            return result;
        }
        CourseSelection courseSelection;
        Map<String, Object> tempMap;
        for (CourseSelection value : courseSelectionList) {
            courseSelection = value;
            tempMap = new HashMap<>();
            tempMap.put("id", courseSelection.getCourseSelectionId());
            tempMap.put("course", courseSelection.getCourse().getName());
            tempMap.put("student", courseSelection.getStudent().getStudentName());
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

    public Integer saveCourseSelectionByCourse(CourseSelection courseSelectionData) {
        CourseSelection courseSelection = null;
        Optional<CourseSelection> op;
        if (courseSelectionData.getCourseSelectionId() != null) {
            op = courseSelectionRepository.findById(courseSelectionData.getCourseSelectionId());
            if (op.isPresent()) {
                courseSelection = op.get();
            }
        }
        Integer maxCourseSlectionId = null;
        if (courseSelection != null) {
            courseSelection = new CourseSelection();
            maxCourseSlectionId = courseSelectionRepository.getMaxId();
            if (maxCourseSlectionId == null) {
                maxCourseSlectionId = 1;
            } else {
                maxCourseSlectionId += 1;
            }
            courseSelection.setCourseSelectionId(maxCourseSlectionId);
        }

        Course relatedCourse;
        Optional<Course> opCourse = courseRepository.findById(courseId);
        if (opCourse.isPresent()) {
            relatedCourse = opCourse.get();
            courseSelection.setCourse(relatedCourse);
        }
        courseSelectionRepository.save(courseSelection);
        return maxCourseSlectionId;
    }
}
