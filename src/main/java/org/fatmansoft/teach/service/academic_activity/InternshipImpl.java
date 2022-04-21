package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Internship;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.InternshipRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class InternshipImpl {
    @Resource
    private InternshipRepository internshipRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> queryAllInternship() {
        List<Object> result = new ArrayList<>();
        List<Internship> internshipList = internshipRepository.findInternshipsByStudent_StudentId(studentId);
        if (internshipList.size() == 0) {
            return result;
        }
        Internship internship;
        Map<String, Object> tempMap;
        for (Internship value : internshipList) {
            internship = value;
            tempMap = new HashMap<>();
            tempMap.put("id", internship.getInternshipId());
            tempMap.put("organization", internship.getOrganization());
            tempMap.put("content", internship.getContent());
            tempMap.put("location", internship.getLocation());
            tempMap.put("startTime", internship.getStartTime());
            tempMap.put("endTime", internship.getEndTime());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryInternship(Integer internshipId) {
        Internship internship = getInternship(internshipId);
        Map<String, Object> resultMap = new HashMap<>();
        if (internship != null) {
            resultMap.put("id", internship.getInternshipId());
            resultMap.put("organization", internship.getOrganization());
            resultMap.put("content", internship.getContent());
            resultMap.put("location", internship.getLocation());
            resultMap.put("startTime", internship.getStartTime());
            resultMap.put("endTime", internship.getEndTime());
        }
        return resultMap;
    }

    public Integer insertInternship(Internship internshipData) {
        Internship internship = getInternship(internshipData.getInternshipId());
        Integer maxInternshipId = null;
        if (internship == null) {
            internship = new Internship();
            maxInternshipId = internshipRepository.getMaxId();
            if (maxInternshipId == null) {
                maxInternshipId = 1;
            } else {
                maxInternshipId += 1;
            }
            internship.setInternshipId(maxInternshipId);
        }
        internship.setContent(internshipData.getContent());
        internship.setLocation(internshipData.getLocation());
        internship.setOrganization(internshipData.getOrganization());
        internship.setStartTime(internshipData.getStartTime());
        internship.setEndTime(internshipData.getEndTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            internship.setStudent(relatedStudent);
        }
        internshipRepository.save(internship);
        return maxInternshipId;
    }

    public void deleteInternship(Integer internshipId) {
        Internship internship = getInternship(internshipId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getInternships().remove(internship);
        }
        internshipRepository.delete(internship);
    }

    private Internship getInternship(Integer internshipId) {
        Internship internship = null;
        Optional<Internship> op;
        if (internshipId != null) {
            op = internshipRepository.findById(internshipId);
            if (op.isPresent()) {
                internship = op.get();
            }
        }
        return internship;
    }
}
