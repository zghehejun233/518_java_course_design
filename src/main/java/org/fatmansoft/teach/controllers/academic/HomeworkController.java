package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.HomeworkService;
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
public class HomeworkController {
    @Resource
    private HomeworkService homeworkService;

    @PostMapping("/homeworkInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse homeworkInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = homeworkService.getAllHomework(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/homeworkEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse homeworkEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = homeworkService.getHomeworkDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/homeworkEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse homeworkEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = homeworkService.saveHomework(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/homeworkDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse homeworkDelete(@Valid @RequestBody DataRequest dataRequest) {
        homeworkService.deleteHomework(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
