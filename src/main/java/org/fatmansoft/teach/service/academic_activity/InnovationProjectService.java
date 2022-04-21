package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.academic_activity.InnovationProject;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class InnovationProjectService {
    @Resource
    private InnovationProjectImpl innovationProject;

    public List<Object> getAllInnovationProject(DataRequest dataRequest) {
        innovationProject.setStudentId(dataRequest.getInteger("studentId"));
        return innovationProject.queryAllInnovationProject();
    }

    public Map<String,Object> getInnovationProjectDetail(DataRequest dataRequest) {
        Integer innovationProjectId = dataRequest.getInteger("id");
        return innovationProject.queryPracticeDetail(innovationProjectId);
    }

    public Integer saveInnovationProject(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        InnovationProject innovationProjectData = new InnovationProject();
        innovationProjectData.setInnovationProjectId(CommonMethod.getInteger(form,"id"));
        innovationProjectData.setContent(CommonMethod.getString(form,"content"));
        innovationProjectData.setPrincipal(CommonMethod.getString(form,"principal"));
        innovationProjectData.setAdvisor(CommonMethod.getString(form,"advisor"));
        innovationProjectData.setOrganization(CommonMethod.getString(form,"organization"));
        innovationProjectData.setDescription(CommonMethod.getString(form,"description"));
        return innovationProject.insertInnovationProject(innovationProjectData);
    }

    public void deleteInnovationProject(DataRequest dataRequest) {
        Integer innovationProjectId = dataRequest.getInteger("id");
        innovationProject.dropPractice(innovationProjectId);
    }
}
