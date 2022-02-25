package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Achievement;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")

public class AchievementController {
    @Autowired
    private AchievementRepository achievementRepository;

    public List getAchievementMapList(String numName){
        List dataList = new ArrayList();
        List<Achievement> aList = achievementRepository.findAchievementListByNumName(numName);
        if(aList == null ||aList.size() ==0){
            return dataList;
        }
        Achievement a;
        Map m;
        for (int i=0;i<aList.size();i++){
            a = aList.get(i);
            m = new HashMap();
            m.put("id",a.getId());
            m.put("studentName",a.getStudentName());
            dataList.add(m);
        }
        return dataList;
    }

    @PostMapping("/achievementInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementInit(@Valid @RequestBody DataRequest datarequest) {
        List dataList = getAchievementMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/achievementQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementQuery(@Valid @RequestBody DataRequest dataRequest){
        return null;
    }

    @PostMapping("/achievementEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditInit(@Valid @RequestBody DataRequest dataRequest) {
        String s = "hello World";
        return CommonMethod.getReturnData(s);
    }

    @PostMapping("/achievementEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditSubmit(@Valid @RequestBody DataRequest dataRequest){
        String s = "Hello World";
        return CommonMethod.getReturnData(s);
    }
}
