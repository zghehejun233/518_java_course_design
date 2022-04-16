package org.fatmansoft.teach.service.student_basic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.EducationExperienceRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class EducationExperienceImpl {
    @Resource
    private EducationExperienceRepository educationExperienceRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> getEducationExperienceMapList() {
        List<Object> result = new ArrayList<>();
        List<EducationExperience> educationExperienceList = educationExperienceRepository.findEducationExperiencesByStudent_StudentId(studentId);
        if (educationExperienceList.size() == 0) {
            return result;
        }
        EducationExperience educationExperience;
        Map<String, Object> tempMap;
        for (EducationExperience value : educationExperienceList) {
            educationExperience = value;
            tempMap = new HashMap<>();
            tempMap.put("id", educationExperience.getEducationExperienceId());
            tempMap.put("schoolName", educationExperience.getSchoolName());
            tempMap.put("level", educationExperience.getLevel());
            tempMap.put("startTime", DateTimeTool.parseDateTime(educationExperience.getStartTime(), "yyyy-MM"));
            tempMap.put("endTime", DateTimeTool.parseDateTime(educationExperience.getEndTime(), "yyyy-MM"));
            tempMap.put("description", educationExperience.getDescription());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> getEducationExperienceDetail(Integer id) {
        EducationExperience educationExperience = null;
        Optional<EducationExperience> op;
        if (id != null) {
            op = educationExperienceRepository.findById(id);
            if (op.isPresent()) {
                educationExperience = op.get();
            }

        }
        Map<String, Object> resultMap = new HashMap<>(16);
        if (educationExperience != null) {
            resultMap.put("id", educationExperience.getEducationExperienceId());
            resultMap.put("schoolName", educationExperience.getSchoolName());
            resultMap.put("level", educationExperience.getLevel());
            resultMap.put("startTime", DateTimeTool.parseDateTime(educationExperience.getStartTime(), "yyyy-MM"));
            resultMap.put("endTime", DateTimeTool.parseDateTime(educationExperience.getEndTime(), "yyyy-MM"));
            resultMap.put("description", educationExperience.getDescription());
        }
        return resultMap;
    }

    public Integer saveEducationExperience(EducationExperience educationExperienceData) {
        EducationExperience educationExperience = null;
        Optional<EducationExperience> op;
        if (educationExperienceData.getEducationExperienceId() != null) {
            op = educationExperienceRepository.findById(educationExperienceData.getEducationExperienceId());
            if (op.isPresent()) {
                educationExperience = op.get();
            }
        }
        Integer maxEducationExperienceId = null;
        if (educationExperience == null) {
            educationExperience = new EducationExperience();
            maxEducationExperienceId = educationExperienceRepository.getMaxId();
            if (maxEducationExperienceId == null) {
                maxEducationExperienceId = 1;
            } else {
                maxEducationExperienceId += 1;
            }
            educationExperience.setEducationExperienceId(maxEducationExperienceId);
        }
        educationExperience.setSchoolName(educationExperienceData.getSchoolName());
        educationExperience.setLevel(educationExperienceData.getLevel());
        educationExperience.setStartTime(educationExperienceData.getStartTime());
        educationExperience.setEndTime(educationExperienceData.getEndTime());
        educationExperience.setDescription(educationExperienceData.getDescription());

        Student relatedStudent = null;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            educationExperience.setStudent(relatedStudent);
        }
        educationExperienceRepository.save(educationExperience);
        return maxEducationExperienceId;
    }

    public void deleteEducationExperience(Integer educationExperienceId) {
        EducationExperience educationExperience = null;
        Optional<EducationExperience> op;
        if (educationExperienceId != null) {
            op = educationExperienceRepository.findById(educationExperienceId);
            if (op.isPresent()) {
                educationExperience = op.get();
            }
        }
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getFamilies().remove(educationExperience);
        }
        educationExperienceRepository.delete(educationExperience);
    }
}
