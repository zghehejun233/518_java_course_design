package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.FamilyService;
import org.fatmansoft.teach.service.student_basic.StudentService;
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
    private StudentService studentService;
    @Resource
    private FamilyService familyService;

    @PostMapping("/studentInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentService.getAllStudent(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("studentEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String,Object> result = studentService.getStudentDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }
    @PostMapping("/studentEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditSubmit(@Valid @RequestBody DataRequest dataRequest){
        Integer result = studentService.saveStudent(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentDelete(@Valid @RequestBody DataRequest dataRequest){
        //TODO
        return CommonMethod.getReturnData("OK");
    }

    @PostMapping("/studentQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentQuery(@Valid @RequestBody DataRequest dataRequest){
        //TODO
        return CommonMethod.getReturnData("OK");
    }

    @PostMapping("/familyInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = familyService.getAllFamily(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/familyEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String,Object> result = familyService.getFamilyDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/familyEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = familyService.saveFamily(dataRequest);
        return CommonMethod.getReturnData(result);
    }
}
