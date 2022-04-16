package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EducationExperienceService {
    @Resource
    private EducationExperienceImpl educationExperience;

    public List<Object> getAllEducationExperience(DataRequest dataRequest) {
        educationExperience.setStudentId(dataRequest.getInteger("studentId"));
        return educationExperience.getEducationExperienceMapList();
    }

    public Map<String, Object> getEducationExperienceDetail(DataRequest dataRequest) {
        Integer educationExperienceId = dataRequest.getInteger("id");
        return educationExperience.getEducationExperienceDetail(educationExperienceId);
    }

    public Integer saveEducationExperience(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        EducationExperience educationExperienceData = new EducationExperience();
        educationExperienceData.setEducationExperienceId(CommonMethod.getInteger(form, "id"));
        educationExperienceData.setSchoolName(CommonMethod.getString(form, "schoolName"));
        educationExperienceData.setLevel(CommonMethod.getString(form,"level"));
        educationExperienceData.setStartTime(CommonMethod.getDate(form, "startTime"));
        educationExperienceData.setEndTime(CommonMethod.getDate(form, "endTime"));
        educationExperienceData.setDescription(CommonMethod.getString(form, "description"));
        return educationExperience.saveEducationExperience(educationExperienceData);
    }

    public void deleteEducationExperience(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        educationExperience.deleteEducationExperience(id);
    }
}
