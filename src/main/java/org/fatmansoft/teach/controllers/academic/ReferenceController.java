package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.ReferenceService;
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
public class ReferenceController {
    @Resource
    private ReferenceService referenceService;

    @PostMapping("/referenceInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = referenceService.getAllReferenceMapList(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/referenceEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = referenceService.getReferenceDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/referenceEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = referenceService.saveReference(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/referenceDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceDelete(@Valid @RequestBody DataRequest dataRequest) {
        referenceService.deleteReference(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
