package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.ScoreService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
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
public class ScoreController {
    @Resource
    private ScoreService scoreService;


    @PostMapping("/scoreInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scoreInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = scoreService.getAllScore(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scoreEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scoreEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = scoreService.getScoreDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scoreEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scoreEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = scoreService.saveScore(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/scoreDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse scoreDelete(@Valid @RequestBody DataRequest dataRequest) {
        scoreService.deleteScore(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
