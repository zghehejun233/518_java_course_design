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
public class SocialRelationController {
    @Resource
    private SocialRelationService socialRelationService;


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
}
