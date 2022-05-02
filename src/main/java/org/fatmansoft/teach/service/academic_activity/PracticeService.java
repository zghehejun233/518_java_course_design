package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 16645
 */
@Service
public class PracticeService {
    @Resource
    private PracticeImpl practice;

    public List<Object> getAllPractice(DataRequest dataRequest) {
        practice.setStudentId(dataRequest.getInteger("studentId"));
        return practice.queryAllPractice();
    }

    public Map<String,Object> getPracticeDetail(DataRequest dataRequest) {
        Integer practiceId = dataRequest.getInteger("id");
        return practice.queryPracticeDetail(practiceId);
    }

    public Integer savePractice(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Practice practiceData = new Practice();
        practiceData.setPracticeId(CommonMethod.getInteger(form,"id"));
        practiceData.setTime(CommonMethod.getTime(form,"time"));
        practiceData.setContent(CommonMethod.getString(form,"content"));
        practiceData.setPrincipal(CommonMethod.getString(form,"principal"));
        practiceData.setOrganization(CommonMethod.getString(form,"organization"));
        practiceData.setName(CommonMethod.getString(form,"name"));
        return practice.insertPractice(practiceData);
    }

    public void deletePractice(DataRequest dataRequest) {
        Integer practiceId = dataRequest.getInteger("id");
        practice.dropPractice(practiceId);
    }
}
