package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.SocialRelation;
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
public class SocialRelationService {
    @Resource
    private SocialRelationImpl socialRelation;

    /**
     * 传回所有社会关系的信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回数组
     */
    public List<Object> getAllSocialRelation(DataRequest dataRequest) {
        socialRelation.setStudentId(dataRequest.getInteger("studentId"));
        return socialRelation.querySocialRelationList();
    }

    /**
     * 传回某个社会关系的详细信息
     *
     * @param dataRequest 接受请求数据
     * @return 返回单个对象的Map
     */
    public Map<String, Object> getSocialRelationDetail(DataRequest dataRequest) {
        Integer socialRelationId = dataRequest.getInteger("id");
        return socialRelation.querySocialRelationDetail(socialRelationId);
    }

    /**
     * 持久化社会关系信息
     *
     * @param dataRequest 请求内容
     * @return integer
     */
    public Integer saveSocialRelation(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        SocialRelation socialRelationData = new SocialRelation();
        socialRelationData.setSocialRelationId(CommonMethod.getInteger(form, "id"));
        socialRelationData.setDescription(CommonMethod.getString(form, "description"));
        return socialRelation.insertSocialRelation(socialRelationData);
    }

    /**
     * 删除社会关系信息
     *
     * @param dataRequest 请求内容
     */
    public void deleteSocialRelation(DataRequest dataRequest) {
        Integer socialRelationId = dataRequest.getInteger("id");
        socialRelation.dropSocialRelation(socialRelationId);
    }
}
