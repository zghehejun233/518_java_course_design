package org.fatmansoft.teach.controllers.student_basic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.EducationExperienceService;
import org.fatmansoft.teach.service.student_basic.FamilyService;
import org.fatmansoft.teach.service.student_basic.SocialRelationService;
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
public class FamilyController {
    @Resource
    private FamilyService familyService;

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

}
