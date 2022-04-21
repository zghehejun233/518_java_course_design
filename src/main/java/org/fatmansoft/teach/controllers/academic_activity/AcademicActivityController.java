package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.AcademicActivityService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class AcademicActivityController {
    @Resource
    private AcademicActivityService academicActivityService;

    @PostMapping("/academicActivityInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse academicActivityInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = academicActivityService.findAllAcademicActivity(dataRequest);
        return CommonMethod.getReturnData(result);
    }
}