package org.fatmansoft.teach.service.student_basic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.FamilyRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class FamilyImpl {
    @Resource
    private FamilyRepository familyRepository;
    @Resource
    private StudentRepository studentRepository;

    public Integer studentId;

    public List<Object> getFamilyMapList() {
        List<Object> result = new ArrayList<>();
        List<Family> familyList = familyRepository.findFamilyByStudent_StudentId(studentId);
        if (familyList.size() == 0) {
            return result;
        }
        Family family;
        Map<String, Object> tempMap;
        for (Family value : familyList) {
            family = value;
            tempMap = new HashMap<>();
            tempMap.put("id", family.getFamilyId());
            tempMap.put("name", family.getName());
            tempMap.put("relation", family.getRelation());
            tempMap.put("sex", family.getSex());
            tempMap.put("age", family.getAge());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> getFamilyDetail(Integer id) {
        Family family = getFamily(id);
        Map<String, Object> resultMap = new HashMap<>(16);
        if (family != null) {
            resultMap.put("id", family.getFamilyId());
            resultMap.put("name", family.getName());
            resultMap.put("relation", family.getRelation());
            resultMap.put("sex", family.getSex());
            resultMap.put("age", family.getAge());
            resultMap.put("description", family.getDescription());
        }
        return resultMap;
    }

    public Integer saveFamily(Family familyData) {
        Family family = getFamily(familyData.getFamilyId());
        Integer maxFamilyId = null;
        if (family == null) {
            family = new Family();
            maxFamilyId = familyRepository.getMaxId();
            if (maxFamilyId == null) {
                maxFamilyId = 1;
            } else {
                maxFamilyId += 1;
            }
            family.setFamilyId(maxFamilyId);
        }
        family.setName(familyData.getName());
        family.setRelation(familyData.getRelation());
        family.setSex(familyData.getSex());
        family.setAge(familyData.getAge());
        family.setDescription(familyData.getDescription());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            family.setStudent(relatedStudent);
        }
        familyRepository.save(family);
        return maxFamilyId;
    }

    public void deleteFamily(Integer familyId) {
        Family family = getFamily(familyId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getFamilies().remove(family);
        }
        familyRepository.delete(family);
    }

    private Family getFamily(Integer familyId) {
        Family family = null;
        Optional<Family> op;
        if (familyId != null) {
            op = familyRepository.findById(familyId);
            if (op.isPresent()) {
                family = op.get();
            }
        }
        return family;
    }
}
