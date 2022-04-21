package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.daily.OutingService;
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
public class OutingController {
    @Resource
    private OutingService outingService;


    @PostMapping("/outingInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse outingInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = outingService.getAllOuting(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/outingEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse outingEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = outingService.getOutingDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/outingEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse outingEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = outingService.saveOuting(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/outingDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse outingDelete(@Valid @RequestBody DataRequest dataRequest) {
        outingService.deleteOuting(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
