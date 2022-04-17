package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.ReferenceService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
        //TODO
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/referenceEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceEditInit(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/referenceEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/referenceDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse referenceDelete(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
        return CommonMethod.getReturnMessageOk();
    }
}
