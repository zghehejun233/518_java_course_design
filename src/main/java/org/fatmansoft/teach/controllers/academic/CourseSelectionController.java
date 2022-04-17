package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.SpringBootSecurityJwtApplication;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.academic.CourseSelectionRepository;
import org.fatmansoft.teach.service.academic.CourseSelectionService;
import org.fatmansoft.teach.service.academic.CourseService;
import org.fatmansoft.teach.util.CommonMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CourseSelectionController {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootSecurityJwtApplication.class);

    @Resource
    private CourseSelectionService courseSelectionService;

    @PostMapping("/courseSelectionInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = courseSelectionService.getAllCourseSelection(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/courseSelectionEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String,Object> result = courseSelectionService.getCourseSelectionDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/courseSelectionEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = courseSelectionService.saveCourseSelection(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/courseSelectionDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionDelete(@Valid @RequestBody DataRequest dataRequest) {
       return CommonMethod.getReturnMessageOk();
    }
}
