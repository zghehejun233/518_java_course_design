package org.fatmansoft.teach.service.student_basic;

import org.assertj.core.util.DateUtil;
import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Validated
public class StudentImpl {
    @Resource
    private StudentRepository studentRepository;

    public List<Object> queryStudentMapList(String content, String type) {
        List<Object> result = new ArrayList<>();
        List<Student> studentList;
        if ("num".equals(type)) {
            studentList = studentRepository.findStudentsByStudentNumContains(content);
        } else if ("name".equals(type)) {
            studentList = studentRepository.findStudentsByStudentNameContains(content);
        } else {
            studentList = studentRepository.findAll();
        }

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
            String studentNameParas = "model=introduce&studentId=" + student.getStudentId();
            tempMap.put("studentName", student.getStudentName());
            tempMap.put("studentNameParas", studentNameParas);

            if (Integer.valueOf(1).equals(student.getSex())) {
                tempMap.put("sex", "男");
            } else if (Integer.valueOf(2).equals(student.getSex())) {
                tempMap.put("sex", "女");
            } else {
                tempMap.put("sex", "性少数");
            }
            tempMap.put("age", student.getAge());
            tempMap.put("phoneNumber", student.getPhoneNumber());
            tempMap.put("school", parseSchool(student.getSchool()));
            tempMap.put("major", student.getMajor());
            tempMap.put("className", student.getClassName());

            String familyParas = "model=family&studentId=" + student.getStudentId();
            Set<Family> families = student.getFamilies();
            tempMap.put("family", "共有" + families.size() + "口人");
            tempMap.put("familyParas", familyParas);

            String socialRelationParas = "model=socialRelation&studentId=" + student.getStudentId();
            Set<SocialRelation> socialRelations = student.getSocialRelations();
            tempMap.put("socialRelation", "共有" + socialRelations.size() + "条社会关系");
            tempMap.put("socialRelationParas", socialRelationParas);

            String educationExperienceParas = "model=educationExperience&studentId=" + student.getStudentId();
            Set<EducationExperience> educationExperiences = student.getEducationExperiences();
            tempMap.put("educationExperience", "共有" + educationExperiences.size() + "条教育经历");
            tempMap.put("educationExperienceParas", educationExperienceParas);

            result.add(tempMap);
        }
        return result;

    }

    Map<String, Object> queryStudentDetail(Integer id) {
        Student student = getStudent(id);
        Map<String, Object> resultMap = new HashMap<>(16);
        if (student != null) {
            resultMap.put("studentNum", student.getStudentNum());
            resultMap.put("studentName", student.getStudentName());
            resultMap.put("sex", student.getSex());
            resultMap.put("age", student.getAge());
            resultMap.put("phoneNumber", student.getPhoneNumber());
            resultMap.put("birthday", student.getBirthday());
            resultMap.put("email", student.getEmail());
            resultMap.put("school", parseSchool(student.getSchool()));
            resultMap.put("major", student.getMajor());
            resultMap.put("className", student.getClassName());

            StringBuilder socialRelationString = new StringBuilder();
            for (SocialRelation socialRelation : student.getSocialRelations()) {
                socialRelationString.append(socialRelation.toString());
            }
            resultMap.put("socialRelation", socialRelationString.toString());

            StringBuilder familyString = new StringBuilder();
            for (Family family : student.getFamilies()) {
                familyString.append(family.toString());
            }
            resultMap.put("family", familyString.toString());

            StringBuilder educationExperienceString = new StringBuilder();
            for (EducationExperience educationExperience : student.getEducationExperiences()) {
                educationExperienceString.append(educationExperience.toString());
            }
            resultMap.put("educationExperience", educationExperienceString.toString());

        }
        return resultMap;
    }

    public Integer insertStudent(@Valid Student studentData) {
        Student student = getStudent(studentData.getStudentId());
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
        student.setSchool(studentData.getSchool());
        student.setMajor(studentData.getMajor());
        student.setClassName(studentData.getClassName());
        Date birthday = studentData.getBirthday();
        Date current = DateUtil.now();
        int age = Integer.parseInt(DateUtil.formatTimeDifference(birthday, current).substring(0, 4));
        student.setAge(age / 365);
        student.setPhoneNumber(studentData.getPhoneNumber());
        student.setBirthday(studentData.getBirthday());
        student.setEmail(studentData.getEmail());
        studentRepository.save(student);
        return maxStudentId;
    }

    public void deleteStudent(Integer studentId) {
        Student student = getStudent(studentId);
        if (student != null) {
            studentRepository.delete(student);
        }
    }

    private Student getStudent(Integer studentId) {
        Student student = null;
        Optional<Student> op;
        if (studentId != null) {
            op = studentRepository.findById(studentId);
            if (op.isPresent()) {
                student = op.get();
            }
        }
        return student;
    }

    private String parseSchool(String schoolCode) {
        String school;
        switch (schoolCode) {
            case "1": {
                school = "软件学院";
                break;
            }
            case "2": {
                school = "其他学院";
                break;
            }
            default: {
                school = "其他学院";
            }
        }
        return school;
    }
}
