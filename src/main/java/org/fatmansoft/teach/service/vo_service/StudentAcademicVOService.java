package org.fatmansoft.teach.service.vo_service;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 16645
 */
@Service
public class StudentAcademicVOService {
    @Resource
    private StudentAcademicVOImpl studentAcademic;

    public List<Object> getAllStudentAcademic(DataRequest dataRequest) {
        return studentAcademic.queryAllStudentAcademic("");
    }
}
