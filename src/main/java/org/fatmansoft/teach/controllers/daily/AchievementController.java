package org.fatmansoft.teach.controllers.daily;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.daily.AchievementService;
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
public class AchievementController {
    @Resource
    private AchievementService achievementService;

    @PostMapping("/achievementInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = achievementService.getAllAchievement(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/achievementEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = achievementService.getAchievementDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/achievementEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = achievementService.saveAchievement(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/achievementDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementDelete(@Valid @RequestBody DataRequest dataRequest) {
        achievementService.deleteAchievement(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
