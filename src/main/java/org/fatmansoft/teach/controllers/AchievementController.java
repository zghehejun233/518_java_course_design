package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.AchievementRepository;
import org.fatmansoft.teach.repository.StudentRepository;
import org.fatmansoft.teach.service.IntroduceService;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/teach")

public class AchievementController {
    @Autowired
    private AchievementRepository achievementRepository;

    @PostMapping("/achievementInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementInit(@Valid @RequestBody DataRequest datarequest){
        String s = "Hello World";
        return CommonMethod.getReturnData(s);
    }
}
