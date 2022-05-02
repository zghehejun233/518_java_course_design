package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.academic_activity.ScientificResult;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.ScientificResultRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Getter
@Setter
public class ScientificResultImpl {
    @Resource
    private ScientificResultRepository scientificResultRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllScientificResult() {
        List<Object> result = new ArrayList<>();
        List<ScientificResult> scientificResultList = scientificResultRepository.findScientificResultsByStudent_StudentId(studentId);
        if (scientificResultList.size() == 0) {
            return result;
        }
        ScientificResult scientificResult;
        Map<String, Object> tempMap;
        for (ScientificResult value : scientificResultList) {
            scientificResult = value;
            tempMap = new HashMap<>();
            tempMap.put("id", scientificResult.getScientificResultId());
            tempMap.put("name", scientificResult.getName());
            tempMap.put("content", scientificResult.getContent());
            tempMap.put("author", scientificResult.getAuthor());
            tempMap.put("level", parseLevel(scientificResult.getLevel()));
            tempMap.put("time", scientificResult.getTime());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryScientificResult(Integer scientificResultId) {
        ScientificResult scientificResult = getScientificResult(scientificResultId);
        Map<String, Object> resultMap = new HashMap<>();
        if (scientificResult != null) {
            resultMap.put("id", scientificResult.getScientificResultId());
            resultMap.put("name", scientificResult.getName());
            resultMap.put("content", scientificResult.getContent());
            resultMap.put("author", scientificResult.getAuthor());
            resultMap.put("level", scientificResult.getLevel());
            resultMap.put("time", scientificResult.getTime());
        }
        return resultMap;
    }

    public Integer insertScientificResult(ScientificResult scientificResultData) {
        ScientificResult scientificResult = getScientificResult(scientificResultData.getScientificResultId());
        Integer maxScientificResultId = null;
        if (scientificResult == null) {
            scientificResult = new ScientificResult();
            maxScientificResultId = scientificResultRepository.getMaxId();
            if (maxScientificResultId == null) {
                maxScientificResultId = 1;
            } else {
                maxScientificResultId += 1;
            }
            scientificResult.setScientificResultId(maxScientificResultId);
        }

        scientificResult.setName(scientificResultData.getName());
        scientificResult.setContent(scientificResultData.getContent());
        scientificResult.setAuthor(scientificResultData.getAuthor());
        scientificResult.setLevel(scientificResultData.getLevel());
        scientificResult.setTime(scientificResultData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            scientificResult.setStudent(relatedStudent);
        }
        scientificResultRepository.save(scientificResult);
        return maxScientificResultId;
    }

    public void dropScientificResult(Integer scientificResultId) {
        ScientificResult scientificResult = getScientificResult(scientificResultId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getScientificResults().remove(scientificResult);
        }
        scientificResultRepository.delete(scientificResult);
    }

    private ScientificResult getScientificResult(Integer scientificResultId) {
        ScientificResult scientificResult = null;
        Optional<ScientificResult> op;
        if (scientificResultId != null) {
            op = scientificResultRepository.findById(scientificResultId);
            if (op.isPresent()) {
                scientificResult = op.get();
            }
        }
        return scientificResult;
    }

    String parseLevel(String levelCode) {
        String level;
        switch (levelCode) {
            case "1": {
                level = "A1";
                break;
            }
            case "2": {
                level = "A2";
                break;
            }
            case "3": {
                level = "B1";
                break;
            }
            case "4": {
                level = "B2";
                break;
            }
            case "5": {
                level = "C";
                break;
            }
            case "6": {
                level = "D";
                break;
            }
            default:
                level = "不明";
        }
        return level;
    }
}
