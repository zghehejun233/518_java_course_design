package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Internship;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class InternshipService {
    @Resource
    private InternshipImpl internship;

    public List<Object> getAllInternship(DataRequest dataRequest) {
        internship.setStudentId(dataRequest.getInteger("studentId"));
        return internship.queryAllInternship();
    }

    public Map<String, Object> getInternshipDetail(DataRequest dataRequest) {
        Integer internshipId = dataRequest.getInteger("id");
        return internship.queryInternship(internshipId);
    }

    public Integer saveInternship(DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Internship internshipData = new Internship();
        internshipData.setInternshipId(CommonMethod.getInteger(form, "id"));
        internshipData.setContent(CommonMethod.getString(form, "content"));
        internshipData.setLocation(CommonMethod.getString(form, "location"));
        internshipData.setOrganization(CommonMethod.getString(form, "organization"));
        internshipData.setStartTime(CommonMethod.getTime(form, "startTime"));
        internshipData.setEndTime(CommonMethod.getTime(form, "endTime"));
        return internship.insertInternship(internshipData);
    }

    public void deleteInternship(DataRequest dataRequest) {
        Integer internshipId = dataRequest.getInteger("id");
        internship.deleteInternship(internshipId);
    }


}
