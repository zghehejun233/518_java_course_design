package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Family;
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
public class FamilyService {
    @Resource
    private FamilyImpl family;

    /**
     * 获取所有家庭成员的信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回家庭成员组成的数组
     */
    public List<Object> getAllFamily(DataRequest dataRequest) {
        // 获取并暂时保存studentId
        family.setStudentId(dataRequest.getInteger("studentId"));
        return family.queryFamilyMapList();
    }

    /**
     * 获取某个家庭成员的信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回一个Map
     */
    public Map<String, Object> getFamilyDetail(DataRequest dataRequest) {
        Integer familyId = dataRequest.getInteger("id");
        return family.queryFamilyDetail(familyId);
    }

    /**
     * 持久化存储家庭成员
     *
     * @param dataRequest 接受请求数据
     * @return 返回存储的id
     */
    public Integer saveFamily(DataRequest dataRequest) {
        // 抽取请求体内的信息
        Map<String, Object> form = dataRequest.getMap("form");
        Family familyData = new Family();
        //  初始化对象
        familyData.setFamilyId(CommonMethod.getInteger(form, "id"));
        familyData.setName(CommonMethod.getString(form, "name"));
        familyData.setRelation((CommonMethod.getString(form, "relation")));
        familyData.setSex(CommonMethod.getString(form, "sex"));
        familyData.setSex(CommonMethod.getString(form, "sex"));
        familyData.setDescription(CommonMethod.getString(form, "description"));
        // 此处应当注意对于参数的传递使用对象
        // 这里的方法导致代码量增加了接近一倍但是可以保证数据的安全性
        // 众所周知使用Map直接传递数据是危险的
        // 接收方对传递方的数据一问三不知
        return family.insertFamily(familyData);
    }

    /**
     * 删除家庭成员
     *
     * @param dataRequest 请求内容
     */
    public void deleteFamily(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        family.dropFamily(id);
    }
}
