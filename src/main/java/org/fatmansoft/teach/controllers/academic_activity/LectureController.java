package org.fatmansoft.teach.controllers.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic_activity.LectureService;
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
public class LectureController {
    @Resource
    private LectureService lectureService;

    @PostMapping("/lectureInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse lectureInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = lectureService.getAllLecture(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/lectureEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse lectureEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = lectureService.getLectureDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/lectureEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse lectureEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = lectureService.saveLecture(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/lectureDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse lectureDelete(@Valid @RequestBody DataRequest dataRequest) {
        lectureService.deleteLecture(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

}
