package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.PracticeRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @author 16645
 */
@Service
@Setter
@Getter
public class PracticeImpl {
    @Resource
    private PracticeRepository practiceRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllPractice() {
        List<Object> result = new ArrayList<>();
        List<Practice> practiceList = practiceRepository.findPracticesByStudent_StudentId(studentId);
        if (practiceList.size() == 0) {
            return result;
        }
        Practice practice;
        Map<String, Object> tempMap;
        for (Practice value : practiceList) {
            practice = value;
            tempMap = new HashMap<>();
            tempMap.put("id", practice.getPracticeId());
            tempMap.put("name", practice.getName());
            tempMap.put("content", practice.getContent());
            tempMap.put("principal", practice.getPrincipal());
            tempMap.put("organization", practice.getOrganization());
            tempMap.put("location", practice.getLocation());
            tempMap.put("time", practice.getTime());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryPracticeDetail(Integer practiceId) {
        Practice practice = getPractice(practiceId);
        Map<String, Object> resultMap = new HashMap<>();
        if (practice != null) {
            resultMap.put("id", practice.getPracticeId());
            resultMap.put("name", practice.getName());
            resultMap.put("content", practice.getContent());
            resultMap.put("principal", practice.getPrincipal());
            resultMap.put("organization", practice.getOrganization());
            resultMap.put("location", practice.getLocation());
            resultMap.put("time", practice.getTime());
        }
        return resultMap;
    }

    private Practice getPractice(Integer practiceId) {
        Practice practice = null;
        Optional<Practice> op;
        if (practice != null) {
            op = practiceRepository.findById(practiceId);
            if (op.isPresent()) {
                practice = op.get();
            }
        }
        return practice;
    }

    public Integer insertPractice(Practice practiceData) {
        Practice practice = getPractice(practiceData.getPracticeId());
        Integer maxPracticeId = null;
        if (practice == null) {
            practice = new Practice();
            maxPracticeId = practiceRepository.getMaxId();
            if (maxPracticeId == null) {
                maxPracticeId = 1;
            } else {
                maxPracticeId += 1;
            }
            practice.setPracticeId(maxPracticeId);
        }
        practice.setName(practiceData.getName());
        practice.setContent(practiceData.getContent());
        practice.setLocation(practiceData.getLocation());
        practice.setOrganization(practiceData.getOrganization());
        practice.setPrincipal(practiceData.getPrincipal());
        practice.setTime(practiceData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            practice.setStudent(relatedStudent);
        }
        practiceRepository.save(practice);
        return maxPracticeId;
    }

    public void dropPractice(Integer practiceId) {
        Practice practice = getPractice(practiceId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getPractices().remove(practice);
        }
        practiceRepository.delete(practice);
    }

}
