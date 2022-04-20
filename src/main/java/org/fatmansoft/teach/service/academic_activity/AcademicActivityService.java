package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AcademicActivityService {
    @Resource
    private AcademicActivityImpl academicActivity;

    public List<Object> findAllAcademicActivity(DataRequest dataRequest) {
        academicActivity.setStudentId(dataRequest.getInteger("studentId"));
        return academicActivity.queryAllAcademicActivity();
    }
}
