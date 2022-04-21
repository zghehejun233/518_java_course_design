package org.fatmansoft.teach.service.daily;


import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {
    @Resource
    private ActivityImpl activity;

    public List<Object> getAllActivity(DataRequest dataRequest) {
        activity.setStudentId(dataRequest.getInteger("studentId"));
        return activity.queryAllActivity();
    }


    public Map<String, Object> getActivityDetail(DataRequest dataRequest) {
        Integer activityId = dataRequest.getInteger("id");
        return activity.queryActivityDetail(activityId);
    }

    public Integer saveActivity(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Activity activityData = new Activity();
        activityData.setActivityId(CommonMethod.getInteger(form, "id"));
        activityData.setName(CommonMethod.getString(form, "name"));
        activityData.setPrincipal(CommonMethod.getString(form, "principal"));
        activityData.setContent(CommonMethod.getString(form, "content"));
        activityData.setLocation(CommonMethod.getString(form, "location"));
        activityData.setTime(CommonMethod.getTime(form, "time"));
        return activity.insertActivity(activityData);
    }

    public void deleteActivity(DataRequest dataRequest) {
        Integer activityId = dataRequest.getInteger("id");
        activity.dropActivity(activityId);
    }

}
