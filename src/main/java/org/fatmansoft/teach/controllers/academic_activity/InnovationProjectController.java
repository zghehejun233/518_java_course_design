package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.InnovationProjectService;
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
public class InnovationProjectController {
    @Resource
    private InnovationProjectService innovationProjectService;

    @PostMapping("/innovationProjectInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse innovationProjectInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = innovationProjectService.getAllInnovationProject(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/innovationProjectEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse innovationProjectEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = innovationProjectService.getInnovationProjectDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/innovationProjectEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse innovationProjectEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = innovationProjectService.saveInnovationProject(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/innovationProjectDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse innovationProjectDelete(@Valid @RequestBody DataRequest dataRequest) {
        innovationProjectService.deleteInnovationProject(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
