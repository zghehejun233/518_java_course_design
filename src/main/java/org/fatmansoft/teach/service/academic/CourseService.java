package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class CourseService {
    @Resource
    private CourseImpl course;

    public List<Object> getAllCourse(DataRequest dataRequest) {
        return course.getCourseMapList("", "");
    }

    public List<Object> getSelectedCourse(DataRequest dataRequest) {
        String content = dataRequest.getString("content");
        String type = dataRequest.getString("type");
        return course.getCourseMapList(content, type);
    }

    public Map<String, Object> getCourseDetail(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("id");
        return course.getCourseDetail(courseId);
    }

    public Integer saveCourse(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Course courseData = new Course();
        courseData.setCourseId(CommonMethod.getInteger(form, "id"));
        courseData.setNum(CommonMethod.getString(form, "num"));
        courseData.setName(CommonMethod.getString(form, "name"));
        courseData.setTeacher(CommonMethod.getString(form, "teacher"));
        courseData.setCredit(CommonMethod.getDouble(form, "credit"));
        courseData.setStart(CommonMethod.getInteger(form, "start"));
        courseData.setEnd(CommonMethod.getInteger(form, "end"));
        courseData.setRecycle(CommonMethod.getInteger(form, "recycle"));
        return course.saveCourse(courseData);
    }

    public void deleteCourse(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        course.deleteCourse(id);
    }
}
