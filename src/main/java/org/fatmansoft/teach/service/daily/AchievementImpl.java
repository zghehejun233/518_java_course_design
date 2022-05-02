package org.fatmansoft.teach.service.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.fatmansoft.teach.models.daily.Achievement;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.daily.AchievementRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class AchievementImpl {
    @Resource
    private AchievementRepository achievementRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> queryAllAchievement() {
        List<Object> result = new ArrayList<>();
        List<Achievement> achievementList = achievementRepository.findAchievementsByStudent_StudentId(studentId);
        if (achievementList.size() == 0) {
            return result;
        }
        Achievement achievement;
        Map<String, Object> tempMap;
        for (Achievement value : achievementList) {
            achievement = value;
            tempMap = new HashMap<>();
            tempMap.put("id", achievement.getAchievementId());
            tempMap.put("name", achievement.getName());
            tempMap.put("level", parseLevel(achievement.getLevel()));
            tempMap.put("type", parseType(achievement.getType()));
            tempMap.put("content", achievement.getContent());
            tempMap.put("organization", achievement.getOrganization());
            tempMap.put("time", achievement.getTime());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryAchievementDetail(Integer achievementId) {
        Achievement achievement = getAchievement(achievementId);
        Map<String, Object> resultMap = new HashMap<>();
        if (achievement != null) {
            resultMap.put("id", achievement.getAchievementId());
            resultMap.put("name", achievement.getName());
            resultMap.put("level", achievement.getLevel());
            resultMap.put("type", achievement.getType());
            resultMap.put("studentName", achievement.getStudent().getStudentName());
            resultMap.put("content", achievement.getContent());
            resultMap.put("organization", achievement.getOrganization());
            resultMap.put("time", achievement.getTime());
        } else {
            Optional<Student> op = studentRepository.findById(studentId);
            op.ifPresent(student -> resultMap.put("studentName", student.getStudentName()));
        }
        return resultMap;
    }

    public Integer insertAchievement(Achievement achievementData) {
        Achievement achievement = getAchievement(achievementData.getAchievementId());
        Integer maxAchievementId = null;
        if (achievement == null) {
            achievement = new Achievement();
            maxAchievementId = achievementRepository.getMaxId();
            if (maxAchievementId == null) {
                maxAchievementId = 1;
            } else {
                maxAchievementId += 1;
            }
            achievement.setAchievementId(maxAchievementId);
        }
        achievement.setName(achievementData.getName());
        achievement.setLevel(achievementData.getLevel());
        achievement.setContent(achievementData.getContent());
        achievement.setOrganization(achievementData.getOrganization());
        achievement.setTime(achievementData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            achievement.setStudent(relatedStudent);
        }
        achievementRepository.save(achievement);
        return maxAchievementId;
    }

    public void dropAchievement(Integer achievementId) {
        Achievement achievement = getAchievement(achievementId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getAchievements().remove(achievement);
        }
        achievementRepository.delete(achievement);
    }

    private Achievement getAchievement(Integer achievementId) {
        Achievement achievement = null;
        Optional<Achievement> op;
        if (achievementId != null) {
            op = achievementRepository.findById(achievementId);
            if (op.isPresent()) {
                achievement = op.get();
            }
        }
        return achievement;
    }

    String parseType(String typeCode) {
        String type;
        switch (typeCode) {
            case "1": {
                type = "奖学金";
                break;
            }
            case "2": {
                type = "证书";
                break;
            }
            default:
                type = " 不明";
        }
        return type;
    }

    String parseLevel(String levelCode) {
        String level;
        switch (levelCode) {
            case "1": {
                level = "院级以下";
                break;
            }
            case "2": {
                level = "院级";
                break;
            }
            case "3": {
                level = "校级";
                break;
            }
            case "4": {
                level = "地市级";
                break;
            }
            case "5": {
                level = "省级";
                break;
            }
            case "6": {
                level = "国级";
                break;
            }
            case "7": {
                level = "国级以上";
                break;
            }
            default:
                level = "不明";
        }
        return level;
    }
}
