package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GuoSurui
 */
@Service
public class CourseService {
    @Resource
    private CourseImpl course;

    public List<Object> getAllCourse(DataRequest dataRequest) {
        return course.getCourseMapList("");
    }
}
