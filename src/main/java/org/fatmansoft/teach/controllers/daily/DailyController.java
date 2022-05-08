package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.vo_service.DailyVOService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class DailyController {
    @Resource
    private DailyVOService dailyVOService;

    @PostMapping("/dailyInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = dailyVOService.getAllDaily(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/dailyQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyQuery(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = dailyVOService.getSelectedDaily(dataRequest);
        return CommonMethod.getReturnData(result);
    }
}
