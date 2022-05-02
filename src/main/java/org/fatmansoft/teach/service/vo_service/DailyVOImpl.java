package org.fatmansoft.teach.service.vo_service;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Setter
@Getter
public class DailyVOImpl {
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;


    public List<Object> queryAllDaily() {
        List<Object> result = new ArrayList<>();
        List<Student> studentList = studentRepository.findStudentListByNumName("");
        if (studentList == null || studentList.size() == 0) {
            return result;
        }
        Student student;
        Map<String, Object> tempMap;
        for (Student value : studentList) {
            student = value;
            tempMap = new HashMap<>();
            tempMap.put("id", student.getStudentId());
            tempMap.put("studentNum", student.getStudentNum());
            tempMap.put("studentName", student.getStudentName());

            String activityParas = "model=activity&studentId=" + student.getStudentId();
            tempMap.put("activity", "参加"+student.getActivities().size()+"次");
            tempMap.put("activityParas", activityParas);

            String outingParas = "model=outing&studentId=" + student.getStudentId();
            tempMap.put("outing", "出门"+student.getOutings().size()+"次");
            tempMap.put("outingParas", outingParas);

            String leaveParas = "model=leave&studentId=" + student.getStudentId();
            tempMap.put("leave", "请假");
            tempMap.put("leaveParas", leaveParas);

            String costParas = "model=cost&studentId=" + student.getStudentId();
            tempMap.put("cost", "消费记录"+student.getCosts().size()+"条");
            tempMap.put("costParas", costParas);

            String achievementParas = "model=achievement&studentId=" + student.getStudentId();
            tempMap.put("achievement", "获得荣誉"+student.getAchievements().size()+"项");
            tempMap.put("achievementParas", achievementParas);

            result.add(tempMap);
        }
        return result;
    }

}
