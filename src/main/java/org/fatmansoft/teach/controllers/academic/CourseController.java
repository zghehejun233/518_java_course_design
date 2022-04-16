package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.service.academic.CourseService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/teach")
public class CourseController {
    @Resource
    private CourseService courseService;

    @PostMapping("/courseInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = courseService.getAllCourse(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/courseEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = courseService.getCourseDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("courseEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = courseService.saveCourse(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/courseDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseDelete(@Valid @RequestBody DataRequest dataRequest) {
        courseService.deleteCourse(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
