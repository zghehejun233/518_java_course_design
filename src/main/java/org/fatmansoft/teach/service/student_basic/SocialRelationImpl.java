package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.repository.student_basic.SocialRelationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class SocialRelationImpl {
    @Resource
    private SocialRelationRepository socialRelationRepository;

    public List<Object> getSocialRelationList(){
        List<Object> result = new ArrayList<>();
        List<SocialRelation> socialRelationList=socialRelationRepository.findAll();
        if (socialRelationList.size()==0){
            return  result;
        }
        SocialRelation socialRelation;
        Map<String,Object> tempMap;
        for(SocialRelation value:socialRelationList){
            socialRelation=value;
            tempMap=new HashMap<>();
            tempMap.put("id",socialRelation.getSocialRelationId());
            tempMap.put("description",socialRelation.getDescription());
            result.add(tempMap);
        }
        return result;
    }
}
