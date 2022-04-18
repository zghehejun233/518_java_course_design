package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.CourseTimeService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CourseTimeController {
    @Resource
    private CourseTimeService courseTimeService;

    @RequestMapping("/courseTimeInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseTimeInit(@Valid @RequestBody DataRequest dataRequest){
        List<Object> result = courseTimeService.getAllCourseTime(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @RequestMapping("/courseTimeEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseTimeEditInit(@Valid @RequestBody DataRequest dataRequest){
        Map<String,Object> result = courseTimeService.getCourseTimeDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @RequestMapping("/courseTimeEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseTimeEditSubmit(@Valid @RequestBody DataRequest dataRequest){
        Integer result = courseTimeService.saveCourseTime(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @RequestMapping("/courseTimeDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseTimeDelete(@Valid @RequestBody DataRequest dataRequest){
        courseTimeService.deleteCourseTime(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
