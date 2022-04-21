package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.CompetitionService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 16645
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CompetitionController {
    @Resource
    private CompetitionService competitionService;

    @PostMapping("/competitionInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse competitionInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = competitionService.getAllCompetition(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/competitionEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse competitionEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = competitionService.getCompetitionDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/competitionEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse competitionEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = competitionService.saveCompetition(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/competitionDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse competitionDelete(@Valid @RequestBody DataRequest dataRequest) {
        competitionService.deleteCompetition(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
