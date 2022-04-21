package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.daily.ActivityService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @PostMapping("/activityInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse activityInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = activityService.getAllActivity(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/activityEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse activityEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = activityService.getActivityDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/activityEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse activityEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = activityService.saveActivity(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/activityDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse activityDelete(@Valid @RequestBody DataRequest dataRequest) {
        activityService.deleteActivity(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
