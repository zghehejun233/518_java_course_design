package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.HomeWork;
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
public class HomeworkService {
    @Resource
    private HomeworkImpl homework;

    public List<Object> getAllHomework(DataRequest dataRequest) {
        homework.setStudentId(dataRequest.getInteger("studentId"));
        return homework.queryAllHomework();
    }

    public Map<String, Object> getHomeworkDetail(DataRequest dataRequest) {
        Integer homeworkId = dataRequest.getInteger("id");
        return homework.queryHomeworkDetail(homeworkId);
    }

    public Integer saveHomework(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        HomeWork homeWorkData = new HomeWork();
        String courseName = CommonMethod.getString(form, "courseName");
        String studentName = CommonMethod.getString(form, "studentName");
        homeWorkData.setContent(CommonMethod.getString(form, "content"));
        homeWorkData.setScore(CommonMethod.getInteger(form, "score"));
        return homework.insertHomework(homeWorkData, courseName, studentName);
    }

    public void deleteHomework(DataRequest dataRequest) {
        Integer homeworkId = dataRequest.getInteger("id");
        homework.dropHomework(homeworkId);
    }
}
