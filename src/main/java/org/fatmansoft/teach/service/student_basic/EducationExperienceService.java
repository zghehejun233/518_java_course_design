package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EducationExperienceService {
    @Resource
    private EducationExperienceImpl educationExperience;

    /**
     * 获取所有
     *
     * @param dataRequest 请求内容
     * @return List
     */
    public List<Object> getAllEducationExperience(DataRequest dataRequest) {
        // 暂存studentId
        educationExperience.setStudentId(dataRequest.getInteger("studentId"));
        return educationExperience.queryEducationExperienceMapList();
    }

    /**
     * 获取详细信息
     *
     * @param dataRequest 请求内容
     * @return Map
     */
    public Map<String, Object> getEducationExperienceDetail(DataRequest dataRequest) {
        Integer educationExperienceId = dataRequest.getInteger("id");
        return educationExperience.queryEducationExperienceDetail(educationExperienceId);
    }


    /**
     * 持久化存储
     *
     * @param dataRequest 请求内容
     * @return Integer
     */
    public Integer saveEducationExperience(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        EducationExperience educationExperienceData = new EducationExperience();
        educationExperienceData.setEducationExperienceId(CommonMethod.getInteger(form, "id"));
        educationExperienceData.setSchoolName(CommonMethod.getString(form, "schoolName"));
        educationExperienceData.setLevel(CommonMethod.getString(form,"level"));
        educationExperienceData.setStartTime(CommonMethod.getDate(form, "startTime"));
        educationExperienceData.setEndTime(CommonMethod.getDate(form, "endTime"));
        educationExperienceData.setDescription(CommonMethod.getString(form, "description"));
        return educationExperience.insertEducationExperience(educationExperienceData);
    }

    /**
     * 删除
     *
     * @param dataRequest 请求内容
     */
    public void deleteEducationExperience(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        educationExperience.dropEducationExperience(id);
    }
}
