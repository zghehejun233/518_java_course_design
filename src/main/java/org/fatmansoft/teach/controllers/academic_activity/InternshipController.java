package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Internship;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.InternshipService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class InternshipController {
    @Resource
    private InternshipService internshipService;

    @PostMapping("/internshipInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse internshipInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = internshipService.getAllInternship(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/internshipEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse internshipEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = internshipService.getInternshipDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/internshipEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse internshipEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = internshipService.saveInternship(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/internshipDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse internshipDelete(@Valid @RequestBody DataRequest dataRequest) {
        internshipService.deleteInternship(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
