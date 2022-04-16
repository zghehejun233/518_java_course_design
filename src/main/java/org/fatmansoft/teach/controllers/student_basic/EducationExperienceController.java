package org.fatmansoft.teach.controllers.student_basic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.EducationExperienceService;
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
public class EducationExperienceController {
    @Resource
    private EducationExperienceService educationExperienceService;

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
