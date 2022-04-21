package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.daily.LeaveService;
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
public class LeaveController {
    @Resource
    private LeaveService leaveService;

    @PostMapping("/leaveInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = leaveService.getAllLeave(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/leaveEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = leaveService.getLeaveDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/leaveEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = leaveService.saveLeave(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/leaveDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveDelete(@Valid @RequestBody DataRequest dataRequest) {
        leaveService.deleteLeave(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
