package org.fatmansoft.teach.service.vo_service;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 16645
 */
@Service
@Setter
@Getter
public class AcademicActivityVOImpl {
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    public List<Object> queryAllAcademicActivity() {
        List<Object> result = new ArrayList<>();
        List<Student> studentList = studentRepository.findStudentListByNumName("");
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

            String practiceParas = "model=practice&studentId=" + student.getStudentId();
            tempMap.put("practice", "shi");
            tempMap.put("practiceParas", practiceParas);

            String competitionParas = "model=competition&studentId=" + student.getStudentId();
            tempMap.put("competition", "shi");
            tempMap.put("competitionParas", competitionParas);

            String innovationProjectParas = "model=innovationProject&studentId=" + student.getStudentId();
            tempMap.put("innovationProject", "shi");
            tempMap.put("innovationProjectParas", innovationProjectParas);

            String internshipParas = "model=internship&studentId=" + student.getStudentId();
            tempMap.put("internship", "shi");
            tempMap.put("internshipParas", internshipParas);

            String lectureParas = "model=lecture&studentId=" + student.getStudentId();
            tempMap.put("lecture", "shi");
            tempMap.put("lectureParas", lectureParas);

            String scientificResultParas = "model=scientificResult&studentId=" + student.getStudentId();
            tempMap.put("scientificResult", "shi");
            tempMap.put("scientificResultParas", scientificResultParas);

            result.add(tempMap);
        }
        return result;
    }

}