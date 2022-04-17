package org.fatmansoft.teach.service.student_basic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.EducationExperience;
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

    public List<Object> queryEducationExperienceMapList() {
        List<Object> result = new ArrayList<>();
        List<EducationExperience> educationExperienceList = educationExperienceRepository
                .findEducationExperiencesByStudent_StudentId(studentId);
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
            tempMap.put("description", educationExperience.getDescription());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryEducationExperienceDetail(Integer id) {
        EducationExperience educationExperience = getEducationExperience(id);
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

    public Integer insertEducationExperience(EducationExperience educationExperienceData) {
        EducationExperience educationExperience = getEducationExperience(educationExperienceData.getEducationExperienceId());
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

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            educationExperience.setStudent(relatedStudent);
        }
        educationExperienceRepository.save(educationExperience);
        return maxEducationExperienceId;
    }

    public void dropEducationExperience(Integer educationExperienceId) {
        EducationExperience educationExperience = getEducationExperience(educationExperienceId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getEducationExperiences().remove(educationExperience);
        }
        educationExperienceRepository.delete(educationExperience);
    }

    private EducationExperience getEducationExperience(Integer educationExperienceId) {
        EducationExperience educationExperience = null;
        Optional<EducationExperience> op;
        if (educationExperienceId != null) {
            op = educationExperienceRepository.findById(educationExperienceId);
            if (op.isPresent()) {
                educationExperience = op.get();
            }
        }
        return educationExperience;
    }
}
