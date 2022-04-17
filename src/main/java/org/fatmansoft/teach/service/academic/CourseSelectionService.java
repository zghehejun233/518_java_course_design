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
        if (dataRequest.getInteger("courseId") != null) {
            courseSelection.setCourseId(dataRequest.getInteger("courseId"));
        } else if (dataRequest.getInteger("studentId") != null) {
            courseSelection.setStudentId(dataRequest.getInteger("studentId"));
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
        courseSelectionData.setCourseSelectionId(CommonMethod.getInteger(form, "id"));
        courseSelectionData.setType(CommonMethod.getString(form, "type"));
        Map<String, Object> studentAndCourseNumName = new HashMap<>();
        studentAndCourseNumName.put("studentName", CommonMethod.getString(form, "studentName"));
        studentAndCourseNumName.put("courseName", CommonMethod.getString(form, "courseName"));
        return courseSelection.saveCourseSelection(courseSelectionData, studentAndCourseNumName);
    }

    public void deleteCourseSelection(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("courseSelectionId");
        courseSelection.deleteCourseSelection(id);
    }
}
