package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author GuoSurui
 */
@Service
public class StudentBasicService {
    @Resource
    private StudentImpl student;
    @Resource
    private FamilyImpl family;

    /**
     * 获取所有学生的信息
     *
     * @param dataRequest 接受请求内容
     * @return 学生数组
     */
    public List<Object> getAllStudent(DataRequest dataRequest) {
        return student.getStudentMapList("");
    }

    /**
     * 传回某个学生的详细信息
     *
     * @param dataRequest 接受请求数据
     * @return 某个学生
     */
    public Map<String, Object> getStudentDetail(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("id");
        return student.getStudentDetail(studentId);
    }

    /**
     * 持久化学生信息
     *
     * @param dataRequest 接受请求内容
     * @return 返回建立的学生id
     */
    public Integer saveStudent(DataRequest dataRequest){
        Map<String,Object> form = dataRequest.getMap("form");
        Student studentData = new Student();
        studentData.setStudentId(CommonMethod.getInteger(form,"id"));
        studentData.setStudentNum(CommonMethod.getString(form,"studentNum"));
        studentData.setStudentName(CommonMethod.getString(form,"studentName"));
        studentData.setSex(CommonMethod.getInteger(form,"sex"));
        studentData.setAge(CommonMethod.getInteger(form,"age"));
        studentData.setPhoneNumber(CommonMethod.getString(form,"phoneNumber"));
        studentData.setBirthday(CommonMethod.getDate(form,"birthday"));
        studentData.setEmail(CommonMethod.getString(form,"email"));
        return student.saveStudent(studentData);
    }

    /**
     * 获取所有家庭成员的信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回家庭成员组成的数组
     */
    public List<Object> getAllFamily(DataRequest dataRequest) {
        family.setStudentId(dataRequest.getInteger("studentId"));
        return family.getFamilyMapList();
    }

    /**
     * 获取某个家庭成员的信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回一个Map
     */
    public Map<String, Object> getFamilyDetail(DataRequest dataRequest) {
        Integer familyId = dataRequest.getInteger("id");
        return family.getFamilyDetail(familyId);
    }

    /**
     * 持久化存储家庭成员
     *
     * @param dataRequest 接受请求数据
     * @return 返回存储的id
     */
    public Integer saveFamily(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Family familyData = new Family();
        familyData.setFamilyId(CommonMethod.getInteger(form, "id"));
        familyData.setName(CommonMethod.getString(form, "name"));
        familyData.setRelation((CommonMethod.getString(form, "relation")));
        familyData.setSex(CommonMethod.getString(form, "sex"));
        familyData.setSex(CommonMethod.getString(form, "sex"));
        familyData.setDescription(CommonMethod.getString(form, "description"));
        return family.saveFamily(familyData);
    }
}
