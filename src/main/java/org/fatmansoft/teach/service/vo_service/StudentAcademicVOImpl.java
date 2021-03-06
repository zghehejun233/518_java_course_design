package org.fatmansoft.teach.service.vo_service;

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

    public List<Object> queryAllStudentAcademic(String content,String type) {
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

            String courseSelectionParas = "model=courseSelection&studentId=" + student.getStudentId();
            tempMap.put("courseSelection", "选了"+student.getCourseSelections().size()+"个课");
            tempMap.put("courseSelectionParas", courseSelectionParas);

            String homeworkParas = "model=homework&studentId=" + student.getStudentId();
            tempMap.put("homework", "交过"+student.getHomeWork().size()+"次作业");
            tempMap.put("homeworkParas", homeworkParas);

            String scoreParas = "model=score&studentId=" + student.getStudentId();
            tempMap.put("score", "有"+student.getScores().size()+"份成绩");
            tempMap.put("scoreParas", scoreParas);

            String checkoutParas = "model=checkout&studentId=" + student.getStudentId();
            tempMap.put("checkout", "上过"+student.getCheckouts().size()+"此课");
            tempMap.put("checkoutParas", checkoutParas);

            result.add(tempMap);
        }
        return result;
    }
}