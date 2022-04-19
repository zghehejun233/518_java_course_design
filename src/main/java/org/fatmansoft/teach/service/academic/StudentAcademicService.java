package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 16645
 */
@Service
public class StudentAcademicService {
    @Resource
    private StudentAcademicImpl studentAcademic;

    public List<Object> getAllStudentAcademic(DataRequest dataRequest) {
        return studentAcademic.queryAllStudentAcademic("");
    }
}
