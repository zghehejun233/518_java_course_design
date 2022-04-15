package org.fatmansoft.teach.service.student_basic;

import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.FamilyRepository;
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
public class FamilyImpl {
    @Resource
    private FamilyRepository familyRepository;

    public List getFamilyMapList(){
        List result = new ArrayList();
        List<Family> familyList = familyRepository.findAll();
        if (familyList==null||familyList.size() == 0) {
            return result;
        }
        Family family;
        Map tempMap;
        for (int i = 0; i < familyList.size(); i++) {
            family = familyList.get(i);
            tempMap = new HashMap();
            tempMap.put("id",family.getFamilyId());
            tempMap.put("name",family.getName());
            tempMap.put("relation" ,family.getRelation());
            tempMap.put("sex",family.getSex());
            tempMap.put("age",family.getAge());
            tempMap.put("description",family.getDescription());
            result.add(tempMap);
        }
        return result;
    }
}
