package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.PracticeService;
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
public class PracticeController {
    @Resource
    private PracticeService practiceService;

    @PostMapping("/practiceInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = practiceService.getAllPractice(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/practiceEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = practiceService.getPracticeDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/practiceEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = practiceService.savePractice(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/practiceDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceDelete(@Valid @RequestBody DataRequest dataRequest) {
        practiceService.deletePractice(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

}
