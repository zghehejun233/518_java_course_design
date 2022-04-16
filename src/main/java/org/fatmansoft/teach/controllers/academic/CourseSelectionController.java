package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.academic.CourseSelectionRepository;
import org.fatmansoft.teach.service.academic.CourseService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CourseSelectionController {
    @Resource
    private CourseService courseService;

    @PostMapping("/courseSelectionInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionInit(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
    }

    @PostMapping("/courseSelectionEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditInit(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
    }

    @PostMapping("/courseSelectionEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
    }

    @PostMapping("/courseSelectionDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionDelete(@Valid @RequestBody DataRequest dataRequest) {
        //TODO
    }
}
