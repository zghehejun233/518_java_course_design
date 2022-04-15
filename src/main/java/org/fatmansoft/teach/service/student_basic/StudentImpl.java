package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
public class StudentImpl {
    @Resource
    private StudentRepository studentRepository;

    public List getStudentMapList(String numName) {
        List result = new ArrayList();
        List<Student> studentList = studentRepository.findStudentListByNumName(numName);
        if (studentList==null||studentList.size() == 0) {
            return result;
        }
        Student student;
        Map tempMap;
        for (int i = 0; i < studentList.size(); i++) {
            student = studentList.get(i);
            tempMap = new HashMap();
            tempMap.put("id",student.getStudentId());
            tempMap.put("studentNum", student.getStudentNum());
            tempMap.put("studentName", student.getStudentName());
            if ("1".equals(student.getSex())) {
                tempMap.put("sex", "男");
            } else if ("2".equals(student.getSex())) {
                tempMap.put("sex", "女");
            }
            tempMap.put("age", student.getAge());
            tempMap.put("phoneNumber", student.getPhoneNumber());
            tempMap.put("birthday", DateTimeTool.parseDateTime(student.getBirthday(),"yyyy-MM-dd"));
            tempMap.put("email", student.getEmail());
            String familyParas = "model=family&studentId=" + student.getStudentId();
            tempMap.put("family","Test");
            tempMap.put("familyParas",familyParas);

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

}
