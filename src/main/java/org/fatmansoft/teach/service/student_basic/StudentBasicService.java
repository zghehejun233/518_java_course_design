package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.payload.request.DataRequest;
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

    /**
     * 获取所有学生的信息
     *
     * @param dataRequest 接受请求内容
     * @return 学生数组
     */
    public List getAllStudent(DataRequest dataRequest){
        return student.getStudentMapList("");
    }

    /**
     * 传回某个学生的详细信息
     *
     * @param dataRequest 接受请求数据
     * @return 某个学生
     */
    public Map<String,Object> getStudentDetail(DataRequest dataRequest){
        Integer studentId= dataRequest.getInteger("id");
        return student.getStudentDetail(studentId);
    }

    @Resource
    private FamilyImpl family;
    public List getAllFamily(DataRequest dataRequest){
        return family.getFamilyMapList();
    }
}
