package org.fatmansoft.teach.service.system;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestService {
    @Autowired
    private StudentRepository studentRepository;
    public void createStudent(){
        Student s;
        Integer id = studentRepository.getMaxId();
        if(id == null) {
            id = 1;
        }
        else {
            id = id + 1;
        }
        s = new Student();
        s.setStudentId(id);
        s.setStudentNum("001");
        s.setStudentName("张三");
        //s.setSex("1");
        s.setAge(12);
        s.setBirthday(new Date());
        studentRepository.save(s);
    }
    public void testDelete(){
        Student s = studentRepository.findById(1).get();
        System.out.println(DateTimeTool.parseDateTime(s.getBirthday(),"yyyy-MM-dd"));
        studentRepository.delete(s);
    }
    public void testMain(){
//        createStudent();
//        testDelete();
    }

}
