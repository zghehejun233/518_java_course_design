package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class CourseTimeService {
    @Resource
    private CourseTimeImpl courseTime;

    public List<Object> getAllCourseTime(DataRequest dataRequest) {
        //TODO
    }

    public Map<String,Object> getCourseTimeDetail(DataRequest dataRequest) {
        //TODO
    }

    public Integer saveCourseTime(DataRequest dataRequest) {
        //TODO
    }

    public void deleteCourseTime (DataRequest dataRequest) {
        //TODO
    }
}
