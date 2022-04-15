package org.fatmansoft.teach.service.student_basic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.SocialRelationRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class SocialRelationImpl {
    @Resource
    private SocialRelationRepository socialRelationRepository;
    @Resource
    private StudentRepository studentRepository;

    public Integer studentId;

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

    public Map<String,Object> getSocialRelationDetail(Integer id){
        SocialRelation socialRelation=null;
        Optional<SocialRelation> op;
        if (id != null) {
            op = socialRelationRepository.findById(id);
            if (op.isPresent()) {
                socialRelation = op.get();
            }

        }
        Map<String, Object> resultMap = new HashMap<>(16);
        if (socialRelation != null) {
            resultMap.put("description",socialRelation.getDescription());
        }
        return resultMap;
    }

    public Integer saveSocialRelation(SocialRelation socialRelationData){
        SocialRelation socialRelation=null;
        Optional<SocialRelation> op;
        if (socialRelationData.getSocialRelationId() != null) {
            op = socialRelationRepository.findById(socialRelationData.getSocialRelationId());
            if (op.isPresent()) {
                socialRelation = op.get();
            }
        }
        Integer maxSocialRelationId = null;
        if (socialRelation == null) {
            socialRelation = new SocialRelation();
            maxSocialRelationId = socialRelationRepository.getMaxId();
            if (maxSocialRelationId == null) {
                maxSocialRelationId = 1;
            } else {
                maxSocialRelationId += 1;
            }
            socialRelation.setSocialRelationId(maxSocialRelationId);
        }
        socialRelation.setDescription(socialRelationData.getDescription());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            socialRelation.setStudent(relatedStudent);
        }
        socialRelationRepository.save(socialRelation);
        return maxSocialRelationId;
    }
}
