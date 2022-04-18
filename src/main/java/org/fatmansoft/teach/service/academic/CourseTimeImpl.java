package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic.CourseTime;
import org.fatmansoft.teach.repository.academic.CourseTimeRepository;
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
public class CourseTimeImpl {
    @Resource
    private CourseTimeRepository courseTimeRepository;

    private Integer courseId;

    public List<Object> queryAllCourseTime(){
     //TODO
    }

    public Map<String,Object> queryCourseTimeDetail(Integer courseTimeId){
        //TODO
    }

    public Integer insertCourseTime(CourseTime courseTimeData) {
        //TODO
    }
    public void dropCourseTime(Integer courseTimeId){
        //TODO
    }
}
