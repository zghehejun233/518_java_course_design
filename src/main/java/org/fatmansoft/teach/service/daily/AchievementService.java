package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.fatmansoft.teach.models.daily.Achievement;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AchievementService {
    @Resource
    private AchievementImpl achievement;

    public List<Object> getAllAchievement(DataRequest dataRequest) {
        achievement.setStudentId(dataRequest.getInteger("studentId"));
        return achievement.queryAllAchievement();
    }


    public Map<String, Object> getAchievementDetail(DataRequest dataRequest) {
        Integer achievementId = dataRequest.getInteger("id");
        return achievement.queryAchievementDetail(achievementId);
    }
    public Integer saveAchievement(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Achievement achievementData = new Achievement();
        achievementData.setAchievementId(CommonMethod.getInteger(form,"id"));
        achievementData.setName(CommonMethod.getString(form,"name"));
        achievementData.setContent(CommonMethod.getString(form,"content"));
        achievementData.setOrganization(CommonMethod.getString(form,"organization"));
        achievementData.setLevel(CommonMethod.getString(form,"level"));
        return achievement.insertAchievement(achievementData);
    }

    public void deleteAchievement(DataRequest dataRequest) {
        Integer achievementId = dataRequest.getInteger("id");
        achievement.dropAchievement(achievementId);
    }

}
