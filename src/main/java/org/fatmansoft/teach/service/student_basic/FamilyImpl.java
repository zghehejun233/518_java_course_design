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
    /**
     * 注入family的DAO层
     * student的DAO层
     */
    @Resource
    private FamilyRepository familyRepository;
    @Resource
    private StudentRepository studentRepository;

    /**
     * 用于短时存储操作的对象标识
     */
    private Integer studentId;

    public List<Object> queryFamilyMapList() {
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
            tempMap.put("relation", parseRelation(family.getRelation()));
            tempMap.put("sex", parseSex(family.getSex()));
            tempMap.put("age", family.getAge());
            result.add(tempMap);
        }
        return result;
    }


    Map<String, Object> queryFamilyDetail(Integer id) {
        Family family = getFamily(id);
        Map<String, Object> resultMap = new HashMap<>(16);
        if (family != null) {
            resultMap.put("id", family.getFamilyId());
            resultMap.put("name", family.getName());
            // 调用方法编码
            resultMap.put("relation", parseRelation(family.getRelation()));
            resultMap.put("sex", parseSex(family.getSex()));
            resultMap.put("age", family.getAge());
            resultMap.put("description", family.getDescription());
        }
        return resultMap;
    }

    public Integer insertFamily(Family familyData) {
        Family family = getFamily(familyData.getFamilyId());
        Integer maxFamilyId = null;
        // 判断是否为添加新数据
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

        // 获取相关的学生对象
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            family.setStudent(relatedStudent);
        }
        familyRepository.save(family);
        return maxFamilyId;
    }

    public void dropFamily(Integer familyId) {
        Family family = getFamily(familyId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getFamilies().remove(family);
        }
        familyRepository.delete(family);
    }

    // 获取对象
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

    // 解码关系
    private String parseRelation(String relationCode) {
        String relation;
        switch (relationCode) {
            case "1": {
                relation = "父亲";
                break;
            }
            case "2": {
                relation = "母亲";
                break;
            }
            case "3": {
                relation = "兄弟姐妹";
                break;
            }
            case "4": {
                relation = "祖辈";
                break;
            }
            default:
                relation = "不明";
        }
        return relation;
    }

    // 编码性别
    private String parseSex(String sexCode) {
        String sex;
        switch (sexCode) {
            case "1": {
                sex = "男";
                break;
            }
            case "2": {
                sex = "女";
                break;
            }
            default:
                sex = "第三性别";
        }
        return sex;
    }
}
