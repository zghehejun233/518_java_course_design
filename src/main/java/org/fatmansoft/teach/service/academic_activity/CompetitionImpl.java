package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Competition;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.CompetitionRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class CompetitionImpl {
    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> queryAllPractice() {
        List<Object> result = new ArrayList<>();
        List<Competition> competitionList = competitionRepository.findCompetitionsByStudent_StudentId(studentId);
        if (competitionList.size() == 0) {
            return result;
        }
        Competition competition;
        Map<String, Object> tempMap;
        for (Competition value : competitionList) {
            competition = value;
            tempMap = new HashMap<>();
            tempMap.put("id", competition.getCompetitionId());
            tempMap.put("studentNum", competition.getStudent().getStudentNum());
            tempMap.put("studentName", competition.getStudent().getStudentName());
            tempMap.put("name", competition.getName());
            tempMap.put("type", parseType(competition.getType()));
            tempMap.put("organizer", competition.getOrganizer());
            tempMap.put("award", competition.getAward());
            tempMap.put("time", DateTimeTool.parseDateTime(competition.getTime(), "yyyy-MM-dd"));
            result.add(tempMap);
        }
        return result;
    }

    public void dropPractice(Integer competitionId) {
        Competition competition = getCompetition(competitionId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getPractices().remove(competition);
        }
        competitionRepository.delete(competition);
    }

    public Map<String, Object> queryCompetition(Integer competitionId) {
        Competition competition = getCompetition(competitionId);
        Map<String, Object> resultMap = new HashMap<>();
        if (competition != null) {
            resultMap.put("id", competition.getCompetitionId());
            resultMap.put("studentName", competition.getStudent().getStudentName());
            resultMap.put("name", competition.getName());
            resultMap.put("type", competition.getType());
            resultMap.put("organizer", competition.getOrganizer());
            resultMap.put("award", competition.getAward());
        }else {
            Optional<Student> op = studentRepository.findById(studentId);
            op.ifPresent(student -> resultMap.put("studentName", student.getStudentName()));
        }
        return resultMap;
    }

    public Integer insertCompetition(Competition competitionData) {
        Competition competition = getCompetition(competitionData.getCompetitionId());
        Integer maxCompetitionId = null;
        if (competition == null) {
            competition = new Competition();
            maxCompetitionId = competitionRepository.getMaxId();
            if (maxCompetitionId == null) {
                maxCompetitionId = 1;
            } else {
                maxCompetitionId += 1;
            }
            competition.setCompetitionId(maxCompetitionId);
        }
        competition.setName(competitionData.getName());
        competition.setType(competitionData.getType());
        competition.setOrganizer(competitionData.getOrganizer());
        competition.setAward(competitionData.getAward());
        competition.setTime(competitionData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            competition.setStudent(relatedStudent);
        }
        competitionRepository.save(competition);
        return maxCompetitionId;
    }

    private Competition getCompetition(Integer competitionId) {
        Competition competition = null;
        Optional<Competition> op;
        if (competitionId != null) {
            op = competitionRepository.findById(competitionId);
            if (op.isPresent()) {
                competition = op.get();
            }
        }
        return competition;
    }

    // 对level进行反编码
    private String parseType(String typeCode) {
        String type;
        switch (typeCode) {
            case "1": {
                type = "院级竞赛";
                break;
            }
            case "2": {
                type = "校级竞赛";
                break;
            }
            case "3": {
                type = "省级竞赛";
                break;
            }
            case "4": {
                type = "国家级竞赛";
                break;
            }
            case "5": {
                type = "世界级竞赛";
                break;
            }
            case "6": {
                type = "宇宙级竞赛";
                break;
            }
            default:
                type = "究极无敌至高竞赛";
        }
        return type;
    }
}
