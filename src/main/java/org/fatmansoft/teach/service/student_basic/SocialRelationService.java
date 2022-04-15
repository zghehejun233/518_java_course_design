package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GuoSurui
 */
@Service
public class SocialRelationService {
    @Resource
    private SocialRelationImpl socialRelation;

    public List<Object> getAllSocialRelation(DataRequest dataRequest){
        return socialRelation.getSocialRelationList();
    }
}
