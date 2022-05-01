package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.SpringBootSecurityJwtApplication;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class CourseSelectionService {
    @Resource
    private CourseSelectionImpl courseSelection;

    public List<Object> getAllCourseSelection(DataRequest dataRequest) {
        try {
            courseSelection.setCourseId(dataRequest.getInteger("courseId"));
        } catch (NullPointerException nullPointerException) {
            SystemApplicationListener.logger.info("courseId设置失败");
        }
        try {
            courseSelection.setStudentId(dataRequest.getInteger("studentId"));
        } catch (NullPointerException nullPointerException) {
            SystemApplicationListener.logger.info("courseId设置失败");
        }
        return courseSelection.getCourseSelectionMapList();
    }

    public Map<String, Object> getCourseSelectionDetail(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("id");
        return courseSelection.getCourseSelectionDetail(courseId);
    }

    public Integer saveCourseSelection(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        CourseSelection courseSelectionData = new CourseSelection();
        Integer courseId = CommonMethod.getInteger(form, "courseId");
        Integer studentId = CommonMethod.getInteger(form, "studentId");
        courseSelectionData.setCourseSelectionId(CommonMethod.getInteger(form, "id"));
        courseSelectionData.setType(CommonMethod.getString(form, "type"));
        return courseSelection.saveCourseSelection(courseSelectionData, courseId, studentId);
    }

    public void deleteCourseSelection(DataRequest dataRequest) {
        Integer courseSelectionId = dataRequest.getInteger("id");
        courseSelection.deleteCourseSelection(courseSelectionId);
    }
}
