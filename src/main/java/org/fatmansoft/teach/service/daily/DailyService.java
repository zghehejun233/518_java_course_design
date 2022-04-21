package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DailyService {
    @Resource
    private DailyImpl daily;

    public List<Object> getAllDaily(DataRequest dataRequest) {
        daily.setStudentId(dataRequest.getInteger("studentId"));
        return daily.queryAllDaily();
    }
}
