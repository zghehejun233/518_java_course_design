package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
public class CourseImpl {
    @Resource
    private CourseRepository courseRepository;

    public List<Object> getCourseMapList(String numName) {
        List<Object> result = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        if (courseList == null || courseList.size() == 0) {
            return result;
        }
        Course course;
        Map<String, Object> tempMap;
        for (Course value : courseList) {
            course = value;
            tempMap = new HashMap<>();
            tempMap.put("id", course.getCourseId());
            tempMap.put("num", course.getNum());
            tempMap.put("name", course.getName());
            tempMap.put("teacher", course.getTeacher());
            tempMap.put("credit", course.getCredit());
            tempMap.put("start", course.getStart());
            tempMap.put("end", course.getEnd());
            tempMap.put("recycle", course.getRecycle());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> getCourseDetail(Integer id) {
        Course course = null;
        Optional<Course> optionalCourse;
        if (id != null) {
            optionalCourse = courseRepository.findById(id);
            if (optionalCourse.isPresent()) {
                course = optionalCourse.get();
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        if (course != null) {
            resultMap.put("id", course.getCourseId());
            resultMap.put("num", course.getNum());
            resultMap.put("name", course.getName());
            resultMap.put("teacher", course.getTeacher());
            resultMap.put("credit", course.getCredit());
            resultMap.put("start", course.getStart());
            resultMap.put("end", course.getEnd());
            resultMap.put("recycle", course.getRecycle());
        }
        return resultMap;
    }

    public Integer saveCourse(Course courseData) {
        Course course = null;
        Optional<Course> optionalCourse;
        if (courseData.getCourseId() != null) {
            optionalCourse = courseRepository.findById(courseData.getCourseId());
            if (optionalCourse.isPresent()) {
                course = optionalCourse.get();
            }
        }
        Integer maxCourseId = null;
        if (course == null) {
            course = new Course();
            maxCourseId = courseRepository.getMaxId();
            if (maxCourseId == null) {
                maxCourseId = 1;
            } else {
                maxCourseId += 1;
            }
            course.setCourseId(maxCourseId);
        }
        course.setNum(courseData.getNum());
        course.setName(courseData.getName());
        course.setTeacher(courseData.getTeacher());
        course.setCredit(courseData.getCredit());
        course.setStart(courseData.getStart());
        course.setEnd(courseData.getEnd());
        course.setRecycle(courseData.getRecycle());
        courseRepository.save(course);
        return maxCourseId;
    }

    public void deleteCourse(Integer courseId) {
        Course course = null;
        Optional<Course> optionalCourse;
        if (courseId != null) {
            optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                course = optionalCourse.get();
            }
        }
        if (course != null) {
            courseRepository.delete(course);
        }
    }
}
