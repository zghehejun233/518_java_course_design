package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
public class StudentImpl {
    @Resource
    private StudentRepository studentRepository;

    public List<Object> getStudentMapList(String numName) {
        List<Object> result = new ArrayList<>();
        List<Student> studentList = studentRepository.findStudentListByNumName(numName);
        if (studentList == null || studentList.size() == 0) {
            return result;
        }
        Student student;
        Map<String, Object> tempMap;
        for (Student value : studentList) {
            student = value;
            tempMap = new HashMap<>();
            tempMap.put("id", student.getStudentId());
            tempMap.put("studentNum", student.getStudentNum());
            tempMap.put("studentName", student.getStudentName());
            if (Integer.valueOf(1).equals(student.getSex())) {
                tempMap.put("sex", "男");
            } else if (Integer.valueOf(2).equals(student.getSex())) {
                tempMap.put("sex", "女");
            } else {
                tempMap.put("sex", "性少数");
            }
            tempMap.put("age", student.getAge());
            tempMap.put("phoneNumber", student.getPhoneNumber());

            String familyParas = "model=family&studentId=" + student.getStudentId();
            Set<Family> families = student.getFamilies();
            tempMap.put("family", "共有" + families.size() + "口人");
            tempMap.put("familyParas", familyParas);

            String socialRelationParas = "model=socialRelation&studentId="+student.getStudentId();
            Set<SocialRelation> socialRelations=student.getSocialRelations();
            tempMap.put("socialRelation","共有" + socialRelations.size() + "条社会关系");
            tempMap.put("socialRelationParas",socialRelationParas);

            String educationExperienceParas = "model=educationExperience&studentId="+student.getStudentId();
            Set<EducationExperience> educationExperiences=student.getEducationExperiences();
            tempMap.put("educationExperience","共有" + educationExperiences.size() + "条教育经历");
            tempMap.put("educationExperienceParas",educationExperienceParas);

            result.add(tempMap);
        }
        return result;

    }

    Map<String, Object> getStudentDetail(Integer id) {
        Student student = null;
        Optional<Student> op;
        if (id != null) {
            op = studentRepository.findById(id);
            if (op.isPresent()) {
                student = op.get();
            }

        }
        Map<String, Object> resultMap = new HashMap<>(16);
        if (student != null) {
            resultMap.put("studentNum", student.getStudentNum());
            resultMap.put("studentName", student.getStudentName());
            resultMap.put("sex", student.getSex());
            resultMap.put("age", student.getAge());
            resultMap.put("phoneNumber", student.getPhoneNumber());
            resultMap.put("birthday", student.getBirthday());
            resultMap.put("email", student.getEmail());
        }
        return resultMap;
    }

    public Integer saveStudent(Student studentData) {
        Student student = null;
        Optional<Student> op;
        if (studentData.getStudentId() != null) {
            op = studentRepository.findById(studentData.getStudentId());
            if (op.isPresent()) {
                student = op.get();
            }
        }
        Integer maxStudentId = null;
        if (student == null) {
            student = new Student();
            maxStudentId = studentRepository.getMaxId();
            if (maxStudentId == null) {
                maxStudentId = 1;
            } else {
                maxStudentId += 1;
            }
            student.setStudentId(maxStudentId);
        }

        student.setStudentNum(studentData.getStudentNum());
        student.setStudentName(studentData.getStudentName());
        student.setSex(studentData.getSex());
        student.setAge(studentData.getAge());
        student.setPhoneNumber(studentData.getPhoneNumber());
        student.setBirthday(studentData.getBirthday());
        student.setEmail(studentData.getEmail());
        studentRepository.save(student);
        return maxStudentId;
    }

    public void deleteStudent(Integer studentId){
        Student student=null;
        Optional<Student> op;
        if (studentId!=null){
            op=studentRepository.findById(studentId);
            if (op.isPresent()){
                student=op.get();
            }
        }
        if (student!=null){
            studentRepository.delete(student);
        }
    }
}
