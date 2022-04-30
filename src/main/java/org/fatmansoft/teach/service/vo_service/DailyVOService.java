package org.fatmansoft.teach.service.vo_service;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DailyVOService {
    @Resource
    private DailyVOImpl daily;

    public List<Object> getAllDaily(DataRequest dataRequest) {
        daily.setStudentId(dataRequest.getInteger("studentId"));
        return daily.queryAllDaily();
    }
}
