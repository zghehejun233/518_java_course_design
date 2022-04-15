package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.StudentBasicService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class StudentBasicController {
    @Resource
    private StudentBasicService studentBasicService;

    @PostMapping("/studentInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentInit(@Valid @RequestBody DataRequest dataRequest) {
        List result = studentBasicService.getAllStudent(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("studentEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map result = studentBasicService.getStudentDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/familyInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyInit(@Valid @RequestBody DataRequest dataRequest) {
        List result = studentBasicService.getAllFamily(dataRequest);
        return CommonMethod.getReturnData(result);
    }
}
