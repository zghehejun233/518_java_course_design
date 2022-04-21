package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.daily.CostService;
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
public class CostController {
    @Resource
    private CostService costService;

    @PostMapping("/costInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = costService.getAllCost(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/costEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = costService.getCostDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/costEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = costService.saveCost(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/costDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costDelete(@Valid @RequestBody DataRequest dataRequest) {
        costService.deleteCost(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
