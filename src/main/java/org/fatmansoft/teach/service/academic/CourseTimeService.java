package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.CourseTime;
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
public class CourseTimeService {
    @Resource
    private CourseTimeImpl courseTime;

    public List<Object> getAllCourseTime(DataRequest dataRequest) {
        courseTime.setCourseId(dataRequest.getInteger("courseId"));
        SystemApplicationListener.logger.info("CourseTime: courseId set: " + courseTime.getCourseId());
        return courseTime.queryAllCourseTime();
    }

    public Map<String, Object> getCourseTimeDetail(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        return courseTime.queryCourseTimeDetail(id);
    }

    public Integer saveCourseTime(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        CourseTime courseTimeData = new CourseTime();
        courseTimeData.setCourseTimeId(CommonMethod.getInteger(form, "id"));
        courseTimeData.setDay(CommonMethod.getInteger(form, "day"));
        courseTimeData.setPeriod(CommonMethod.getInteger(form, "period"));
        return courseTime.insertCourseTime(courseTimeData);
    }

    public void deleteCourseTime(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        courseTime.dropCourseTime(id);
    }
}
