package org.fatmansoft.teach.service.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.daily.AchievementRepository;
import org.fatmansoft.teach.repository.daily.ActivityRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class ActivityImpl {
    @Resource
    private ActivityRepository activityRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;


    List<Object> queryAllActivity() {
        List<Object> result = new ArrayList<>();
List<Activity> activityList = activityRepository.findActivitiesByStudent_StudentId(studentId);
        if (activityList.size() == 0) {
            return result;
        }
        Activity activity;
        Map<String, Object> tempMap;
        for (Activity value : activityList) {
            activity = value;
            tempMap = new HashMap<>();
            tempMap.put("id",activity.getActivityId());
            tempMap.put("name",activity.getName());
            tempMap.put("principal",activity.getPrincipal());
            tempMap.put("content",activity.getContent());
            tempMap.put("location",activity.getLocation());
            tempMap.put("time",activity.getTime());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryActivityDetail(Integer activityId) {
       Activity activity = getActivity(activityId);
        Map<String, Object> resultMap = new HashMap<>();
        if (activity != null) {
            resultMap.put("id",activity.getActivityId());
            resultMap.put("name",activity.getName());
            resultMap.put("principal",activity.getPrincipal());
            resultMap.put("content",activity.getContent());
            resultMap.put("location",activity.getLocation());
            resultMap.put("time",activity.getTime());
        }
        return resultMap;
    }

    public Integer insertActivity(Activity activityData) {
        Activity activity = getActivity(activityData.getActivityId());
        Integer maxActivityId = null;
        if (activity==null){
            activity = new Activity();
            maxActivityId = activityRepository.getMaxId();
            if (maxActivityId==null){
                maxActivityId=1;
            }else {maxActivityId+=1;}
            activity.setActivityId(maxActivityId);
        }
        activity.setName(activityData.getName());
        activity.setPrincipal(activityData.getPrincipal());
        activity.setContent(activityData.getContent());
        activity.setLocation(activityData.getLocation());
        activity.setTime(activityData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            activity.setStudent(relatedStudent);
        }
        activityRepository.save(activity);
        return maxActivityId;
    }

    public void dropActivity(Integer activityId) {
        Activity activity = getActivity(activityId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getActivities().remove(activity);
        }
        activityRepository.delete(activity);
    }

    private Activity getActivity(Integer activityId) {
        Activity activity = null;
        Optional<Activity> op;
        if (activityId != null) {
            op = activityRepository.findById(activityId);
            if (op.isPresent()) {
                activity = op.get();
            }
        }
        return activity;
    }
}
