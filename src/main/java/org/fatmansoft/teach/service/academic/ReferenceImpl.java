package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic.Reference;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.ReferenceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class ReferenceImpl {
    @Resource
    private ReferenceRepository referenceRepository;
    @Resource
    private CourseRepository courseRepository;

    private Integer courseId;

    public List<Object> queryReferenceMapList(){
        //TODO
    }

    public Map<String,Object> queryReferenceDetail(){
        //TODO
    }

    public Integer insertReference(Reference referenceData){
        //TODO
    }

    public void dropReference(){
        //TODO
    }
}
