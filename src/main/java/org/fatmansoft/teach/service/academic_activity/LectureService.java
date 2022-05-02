package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LectureService {
    @Resource
    private LectureImpl lecture;

    public List<Object> getAllLecture(DataRequest dataRequest) {
        lecture.setStudentId(dataRequest.getInteger("studentId"));
        return lecture.queryAllLecture();
    }


    public Map<String, Object> getLectureDetail(DataRequest dataRequest) {
        Integer lectureId = dataRequest.getInteger("id");
        return lecture.queryLectureDetail(lectureId);
    }


    public Integer saveLecture(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Lecture lectureData = new Lecture();
        lectureData.setLectureId(CommonMethod.getInteger(form,"id"));
        lectureData.setTheme(CommonMethod.getString(form,"theme"));
        lectureData.setPresenter(CommonMethod.getString(form,"presenter"));
        lectureData.setContent(CommonMethod.getString(form,"content"));
        lectureData.setLocation(CommonMethod.getString(form,"location"));
        lectureData.setTime(CommonMethod.getTime(form,"time"));
        return lecture.insertLecture(lectureData);
    }
    public void deleteLecture(DataRequest dataRequest) {
        Integer lectureId= dataRequest.getInteger("id");
        lecture.dropLecture(lectureId);
    }
}
