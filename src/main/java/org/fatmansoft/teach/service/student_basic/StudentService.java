package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintDeclarationException;
import java.util.List;
import java.util.Map;


/**
 * @author GuoSurui
 */
@Service
public class StudentService {
    @Resource
    private StudentImpl student;

    /**
     * 获取所有学生的信息
     *
     * @param dataRequest 接受请求内容
     * @return 学生数组
     */
    public List<Object> getAllStudent(DataRequest dataRequest) {
        return student.queryStudentMapList("", "");
    }

    /**
     * 获取筛选学生详细信息
     *
     * @param dataRequest 接受请求内容
     * @return 数组
     */
    public List<Object> getSelectedMapList(DataRequest dataRequest) {
        String content = dataRequest.getString("content");
        String type = dataRequest.getString("type");
        return student.queryStudentMapList(content, type);
    }


    /**
     * 传回某个学生的详细信息
     *
     * @param dataRequest 接受请求数据
     * @return 某个学生
     */
    public Map<String, Object> getStudentDetail(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("id");
        return student.queryStudentDetail(studentId);
    }

    /**
     * 持久化学生信息
     *
     * @param dataRequest 接受请求内容
     * @return 返回建立的学生id
     */
    public Integer saveStudent(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Student studentData = new Student();
        studentData.setStudentId(CommonMethod.getInteger(form, "id"));
        studentData.setStudentNum(CommonMethod.getString(form, "studentNum"));
        studentData.setStudentName(CommonMethod.getString(form, "studentName"));
        studentData.setSex(CommonMethod.getInteger(form, "sex"));
        studentData.setPhoneNumber(CommonMethod.getString(form, "phoneNumber"));
        studentData.setBirthday(CommonMethod.getDate(form, "birthday"));
        studentData.setEmail(CommonMethod.getString(form, "email"));
        studentData.setMajor(CommonMethod.getString(form,"major"));
        studentData.setSchool(CommonMethod.getString(form,"school"));
        studentData.setClassName(CommonMethod.getString(form,"className"));
        return student.insertStudent(studentData);
    }

    /**
     * 删除学生信息
     *
     * @param dataRequest 请求内容
     */
    public void deleteStudent(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        student.deleteStudent(id);
    }
}
