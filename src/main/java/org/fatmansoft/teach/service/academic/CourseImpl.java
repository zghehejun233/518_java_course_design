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
}
