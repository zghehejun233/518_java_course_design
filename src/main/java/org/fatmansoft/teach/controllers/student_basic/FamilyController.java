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
import javax.validation.ValidationException;
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
        // 进行填充异常的捕获
        try {
            Integer result = familyService.saveFamily(dataRequest);
            return CommonMethod.getReturnData(result);
        } catch (ValidationException validationException) {
            String temp = validationException.getLocalizedMessage();
            // 如果填充字段有问题则抛出400
            // 通过字符串运算调整返回信息的内容
            return CommonMethod.getReturnMessage("400", temp.substring(temp.substring(0, temp.indexOf(":")).length() + 2));
        } catch (Exception e) {
            //  捕获所有异常并直接抛出
            return CommonMethod.getReturnMessage("500", "Uncaught");
        }
    }

    @PostMapping("/familyDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse familyDelete(@Valid @RequestBody DataRequest dataRequest) {
        familyService.deleteFamily(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

}
