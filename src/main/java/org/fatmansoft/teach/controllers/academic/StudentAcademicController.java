package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.StudentAcademicService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class StudentAcademicController {
    @Resource
    private StudentAcademicService studentAcademicService;

    @PostMapping("/studentAcademicInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentAcademicInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentAcademicService.getAllStudentAcademic(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentAcademicEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentAcademicEditInit(@Valid @RequestBody DataRequest dataRequest) {
        return CommonMethod.getReturnMessageOk();
    }

}
