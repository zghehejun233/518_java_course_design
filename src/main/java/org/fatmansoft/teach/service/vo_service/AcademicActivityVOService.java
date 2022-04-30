package org.fatmansoft.teach.service.vo_service;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AcademicActivityVOService {
    @Resource
    private AcademicActivityVOImpl academicActivity;

    public List<Object> findAllAcademicActivity(DataRequest dataRequest) {
        academicActivity.setStudentId(dataRequest.getInteger("studentId"));
        return academicActivity.queryAllAcademicActivity();
    }
}
