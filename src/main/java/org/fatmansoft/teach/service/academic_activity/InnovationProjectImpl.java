package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.InnovationProject;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.InnovationProjectRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Getter
@Setter
public class InnovationProjectImpl {
    @Resource
    private InnovationProjectRepository innovationProjectRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> queryAllInnovationProject() {
        List<Object> result = new ArrayList<>();
        List<InnovationProject> innovationProjectList = innovationProjectRepository
                .findInnovationProjectsByStudent_StudentId(studentId);
        if (innovationProjectList.size() == 0) {
            return result;
        }
        InnovationProject innovationProject;
        Map<String, Object> tempMap;
        for (InnovationProject value : innovationProjectList) {
            innovationProject = value;
            tempMap = new HashMap<>();
            tempMap.put("id", innovationProject.getInnovationProjectId());
            tempMap.put("content", innovationProject.getContent());
            tempMap.put("principal", innovationProject.getPrincipal());
            tempMap.put("advisor", innovationProject.getAdvisor());
            tempMap.put("organization", innovationProject.getOrganization());
            tempMap.put("description", innovationProject.getDescription());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryPracticeDetail(Integer innovationProjectId) {
        InnovationProject innovationProject = getInnovationProject(innovationProjectId);
        Map<String, Object> resultMap = new HashMap<>();
        if (innovationProject != null) {
            resultMap.put("id", innovationProject.getInnovationProjectId());
            resultMap.put("content", innovationProject.getContent());
            resultMap.put("principal", innovationProject.getPrincipal());
            resultMap.put("advisor", innovationProject.getAdvisor());
            resultMap.put("organization", innovationProject.getOrganization());
            resultMap.put("description", innovationProject.getDescription());
        }
        return resultMap;
    }

    public Integer insertInnovationProject(InnovationProject innovationProjectData) {
        InnovationProject innovationProject = getInnovationProject(innovationProjectData.getInnovationProjectId());
        Integer maxInnovationProjectId = null;
        if (innovationProject == null) {
            innovationProject = new InnovationProject();
            maxInnovationProjectId = innovationProjectRepository.getMaxId();
            if (maxInnovationProjectId == null) {
                maxInnovationProjectId = 1;
            } else {
                maxInnovationProjectId += 1;
            }
            innovationProject.setInnovationProjectId(maxInnovationProjectId);
        }
        innovationProject.setContent(innovationProjectData.getContent());
        innovationProject.setPrincipal(innovationProjectData.getPrincipal());
        innovationProject.setAdvisor(innovationProjectData.getAdvisor());
        innovationProject.setOrganization(innovationProjectData.getOrganization());
        innovationProject.setDescription(innovationProjectData.getDescription());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            innovationProject.setStudent(relatedStudent);
        }
        innovationProjectRepository.save(innovationProject);
        return maxInnovationProjectId;
    }

    public void dropPractice(Integer innovationProjectid) {
        InnovationProject innovationProject = getInnovationProject(innovationProjectid);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getPractices().remove(innovationProject);
        }
        innovationProjectRepository.delete(innovationProject);
    }


    InnovationProject getInnovationProject(Integer innovationProjectId) {
        InnovationProject innovationProject = null;
        Optional<InnovationProject> op;
        if (innovationProjectId != null) {
            op = innovationProjectRepository.findById(innovationProjectId);
            if (op.isPresent()) {
                innovationProject = op.get();
            }
        }
        return innovationProject;
    }
}
