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

    public List<Object> queryAcademicActivity(String content, String type) {
        List<Object> result = new ArrayList<>();
        List<Student> studentList ;
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
            tempMap.put("studentName", student.getStudentName());

            String practiceParas = "model=practice&studentId=" + student.getStudentId();
            tempMap.put("practice", "实践"+student.getPractices().size());
            tempMap.put("practiceParas", practiceParas);

            String competitionParas = "model=competition&studentId=" + student.getStudentId();
            tempMap.put("competition", "参赛"+student.getCompetitions().size()+"次");
            tempMap.put("competitionParas", competitionParas);

            String innovationProjectParas = "model=innovationProject&studentId=" + student.getStudentId();
            tempMap.put("innovationProject", "参与"+student.getInnovationProjects().size()+"项项目");
            tempMap.put("innovationProjectParas", innovationProjectParas);

            String internshipParas = "model=internship&studentId=" + student.getStudentId();
            tempMap.put("internship", "有"+student.getInternships().size()+"次实习");
            tempMap.put("internshipParas", internshipParas);

            String lectureParas = "model=lecture&studentId=" + student.getStudentId();
            tempMap.put("lecture", "参加"+student.getLectures().size()+"次讲座");
            tempMap.put("lectureParas", lectureParas);

            String scientificResultParas = "model=scientificResult&studentId=" + student.getStudentId();
            tempMap.put("scientificResult", "有"+student.getScientificResults()+"项成果");
            tempMap.put("scientificResultParas", scientificResultParas);

            result.add(tempMap);
        }
        return result;
    }

}
