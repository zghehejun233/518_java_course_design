package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.vo_service.StudentAcademicVOService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class StudentAcademicVOController {
    @Resource
    private StudentAcademicVOService studentAcademicVOService;

    @PostMapping("/studentAcademicInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentAcademicInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentAcademicVOService.getAllStudentAcademic(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentAcademicQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentAcademicQuery(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentAcademicVOService.getSelectedStudentAcademic(dataRequest);
        return CommonMethod.getReturnData(result);
    }
    @PostMapping("/studentAcademicEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentAcademicEditInit(@Valid @RequestBody DataRequest dataRequest) {
        return CommonMethod.getReturnMessageOk();
    }

}
