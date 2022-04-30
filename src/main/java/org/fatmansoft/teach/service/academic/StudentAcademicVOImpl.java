package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 16645
 */
@Service
public class StudentAcademicVOImpl {
    @Resource
    private StudentRepository studentRepository;

    public List<Object> queryAllStudentAcademic(String numName) {
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

            String courseSelectionParas = "model=courseSelection&studentId=" + student.getStudentId();
            tempMap.put("courseSelection", "选课记录");
            tempMap.put("courseSelectionParas", courseSelectionParas);

            String homeworkParas = "model=homework&studentId=" + student.getStudentId();
            tempMap.put("homework", "作业");
            tempMap.put("homeworkParas", homeworkParas);

            String scoreParas = "model=score&studentId=" + student.getStudentId();
            tempMap.put("score", "成绩");
            tempMap.put("scoreParas", scoreParas);

            String checkoutParas = "model=checkout&studentId=" + student.getStudentId();
            tempMap.put("checkout", "作业");
            tempMap.put("checkoutParas", checkoutParas);

            result.add(tempMap);
        }
        return result;
    }
}