package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.CourseSelection;
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
public class CourseSelectionService {
    @Resource
    private CourseSelectionImpl courseSelection;

    public List<Object> getAllCourseSelection(DataRequest dataRequest) {
        courseSelection.setCourseId(dataRequest.getInteger("courseId"));
        courseSelection.setStudentId(dataRequest.getInteger("studentId"));
        return courseSelection.getCourseSelectionMapList();
    }

    public Map<String,Object> getCourseSelectionDetail(DataRequest dataRequest){
        Integer courseId = dataRequest.getInteger("id");
        return courseSelection.getCourseSelectionDetail(courseId);
    }

    public Integer saveCourseSelection(DataRequest dataRequest) {
        Map<String,Object> form = dataRequest.getMap("form");
        CourseSelection courseSelectionData = new CourseSelection();
        courseSelectionData.setCourseSelectionId(CommonMethod.getInteger(form,"id"));
        return courseSelection.saveCourseSelectionByCourse(courseSelectionData);
    }
}
