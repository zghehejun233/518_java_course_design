package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.EducationExperienceService;
import org.fatmansoft.teach.service.student_basic.FamilyService;
import org.fatmansoft.teach.service.student_basic.SocialRelationService;
import org.fatmansoft.teach.service.student_basic.StudentService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.crypto.Data;
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
    @Resource
    private SocialRelationService socialRelationService;
    @Resource
    private EducationExperienceService educationExperienceService;

    @PostMapping("/studentInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentService.getAllStudent(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("studentEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = studentService.getStudentDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = studentService.saveStudent(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentDelete(@Valid @RequestBody DataRequest dataRequest) {
        studentService.deleteStudent(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/studentQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentQuery(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentService.getSelectedMapList(dataRequest);
        return CommonMethod.getReturnData(result);
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
        Map<String, Object> result = familyService.getFamilyDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/familyEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = familyService.saveFamily(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/familyDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyDelete(@Valid @RequestBody DataRequest dataRequest) {
        familyService.deleteFamily(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/socialRelationInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse socialRelationInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = socialRelationService.getAllSocialRelation(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/socialRelationEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse socialRelationEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = socialRelationService.getSocialRelationDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/socialRelationEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse socialRelationEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = socialRelationService.saveSocialRelation(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/socialDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse socialRelationDelete(@Valid @RequestBody DataRequest dataRequest) {
        socialRelationService.deleteSocialRelation(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/educationExperienceInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse educationExperienceInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = educationExperienceService.getAllEducationExperience(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/educationExperienceEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse educationExperienceEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = educationExperienceService.getEducationExperienceDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/educationExperienceEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse educationExperienceEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = educationExperienceService.saveEducationExperience(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/educationExperienceDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse educationExperienceDelete(@Valid @RequestBody DataRequest dataRequest) {
        educationExperienceService.deleteEducationExperience(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
