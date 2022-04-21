package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.ScientificResultService;
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
public class ScientificResultController {
    @Resource
    private ScientificResultService scientificResultService;

    @PostMapping("/scientificResultInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scientificResultInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = scientificResultService.getAllScientificResult(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scientificResultEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scientificResultEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = scientificResultService.getScientificResult(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scientificResultEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scientificResultEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = scientificResultService.saveScientificResult(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scientificResultDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scientificResultDelete(@Valid @RequestBody DataRequest dataRequest) {
        scientificResultService.deleteScientificResult(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

}
