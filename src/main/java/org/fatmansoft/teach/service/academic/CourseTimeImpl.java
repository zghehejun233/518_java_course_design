package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseTime;
import org.fatmansoft.teach.models.academic.Reference;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.CourseTimeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class CourseTimeImpl {
    @Resource
    private CourseTimeRepository courseTimeRepository;
    @Resource
    private CourseRepository courseRepository;

    private Integer courseId;

    public List<Object> queryAllCourseTime() {
        List<Object> result = new ArrayList<>();
        List<CourseTime> courseTimeList = courseTimeRepository.findAll();
        if (courseTimeList.size() == 0) {
            return result;
        }
        CourseTime courseTime;
        Map<String, Object> tempMap;
        for (CourseTime value : courseTimeList) {
            courseTime = value;
            tempMap = new HashMap<>();
            tempMap.put("id", courseTime.getCourseTimeId());
            tempMap.put("day", courseTime.getDay());
            tempMap.put("period", courseTime.getPeriod());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryCourseTimeDetail(Integer courseTimeId) {
        CourseTime courseTime = getCourseTime(courseTimeId);
        Map<String, Object> resultMap = new HashMap<>();
        if (courseTime != null) {
            resultMap.put("id", courseTime.getCourseTimeId());
            resultMap.put("day", courseTime.getDay());
            resultMap.put("period", courseTime.getPeriod());
        }
        return resultMap;
    }

    public Integer insertCourseTime(CourseTime courseTimeData) {
        CourseTime courseTime = getCourseTime(courseTimeData.getCourseTimeId());
        Integer maxCourseTimeId = null;
        if (courseTime == null) {
            courseTime = new CourseTime();
            maxCourseTimeId = courseTimeRepository.getMaxId();
            if (maxCourseTimeId == null) {
                maxCourseTimeId = 1;
            } else {
                maxCourseTimeId += 1;
            }
            courseTime.setCourseTimeId(maxCourseTimeId);
        }
        courseTime.setDay(courseTimeData.getDay());
        courseTime.setPeriod(courseTimeData.getPeriod());

        Course relatedCourse;
        Optional<Course> opCourse = courseRepository.findById(courseId);
        if (opCourse.isPresent()) {
            relatedCourse = opCourse.get();
            Set<Course> courses;
            courses = courseTime.getCourses();
            if (courses == null) {
                courses = new HashSet<>();
            }
            courses.add(relatedCourse);
            courseTime.setCourses(courses);
        }
        courseTimeRepository.save(courseTime);
        return maxCourseTimeId;
    }

    public void dropCourseTime(Integer courseTimeId) {
        CourseTime courseTime = getCourseTime(courseTimeId);
        Course relatedCourse;
        Optional<Course> opCourse = courseRepository.findById(courseId);
        if (opCourse.isPresent()) {
            relatedCourse = opCourse.get();
            relatedCourse.getCourseTimes().remove(courseTime);
        }
    }

    private CourseTime getCourseTime(Integer courseTimeId) {
        CourseTime courseTime = null;
        Optional<CourseTime> op;
        if (courseTimeId != null) {
            op = courseTimeRepository.findById(courseTimeId);
            if (op.isPresent()) {
                courseTime = op.get();
            }
        }
        return courseTime;
    }
}
