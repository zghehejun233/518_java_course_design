package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.FamilyRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
public class FamilyImpl {
    @Resource
    private FamilyRepository familyRepository;
    @Resource
    private StudentRepository studentRepository;

    public List getFamilyMapList(){
        List result = new ArrayList();
        List<Family> familyList = familyRepository.findAll();
        if (familyList==null||familyList.size() == 0) {
            return result;
        }
        Family family;
        Map tempMap;
        for (int i = 0; i < familyList.size(); i++) {
            family = familyList.get(i);
            tempMap = new HashMap();
            tempMap.put("id",family.getFamilyId());
            tempMap.put("name",family.getName());
            tempMap.put("relation" ,family.getRelation());
            tempMap.put("sex",family.getSex());
            tempMap.put("age",family.getAge());
            tempMap.put("description",family.getDescription());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> getFamilyDetail(Integer id) {
        Family family=null;
        Optional<Family> op;
        if (id != null) {
            op = familyRepository.findById(id);
            if (op.isPresent()) {
                family = op.get();
            }

        }
        Map<String, Object> resultMap = new HashMap<>(16);
        if (family != null) {
            resultMap.put("id",family.getFamilyId());
            resultMap.put("name",family.getName());
            resultMap.put("relation" ,family.getRelation());
            resultMap.put("sex",family.getSex());
            resultMap.put("age",family.getAge());
            resultMap.put("description",family.getDescription());
        }
        return resultMap;
    }

    public Integer saveFamily(Family familyData,Integer studentId){
        Family family=null;
        Optional<Family> op;
        if (familyData.getFamilyId()!=null){
            op = familyRepository.findById(familyData.getFamilyId());
            if (op.isPresent()){
                family=op.get();
            }
        }
        Integer maxFamilyId=null;
        if (family==null){
            family = new Family();
             maxFamilyId= familyRepository.getMaxId();
            if (maxFamilyId==null){
                maxFamilyId=1;
            }else {
                maxFamilyId+=1;
            }
            family.setFamilyId(maxFamilyId);
        }
        family.setName(familyData.getName());
        family.setRelation(familyData.getRelation());
        family.setSex(familyData.getSex());
        family.setAge(familyData.getAge());
        family.setDescription(familyData.getDescription());

        Student relatedStudent;
        Optional<Student> opStudent=studentRepository.findById(studentId);
        if (opStudent.isPresent()){
            relatedStudent=opStudent.get();
            family.setStudent(relatedStudent);
        }
        familyRepository.save(family);
        return maxFamilyId;
    }
}
