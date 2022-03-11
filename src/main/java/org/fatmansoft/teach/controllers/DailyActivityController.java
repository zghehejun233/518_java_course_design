package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Achievement;
import org.fatmansoft.teach.models.CourseSelection;
import org.fatmansoft.teach.models.DailyActivity;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.DailyActivityRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.*;

// origins： 允许可访问的域列表 *通配符表示全部
// maxAge:准备响应前的缓存持续的最大时间（以秒为单位）
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class DailyActivityController {
    @Autowired
    private DailyActivityRepository dailyActivityRepository;

    public List getDailyActivityMapList(String numName) {
        List dataList = new ArrayList();
        List<DailyActivity> dList = dailyActivityRepository.findAll();
        if (dList == null || dList.size() == 0) {
            return dataList;
        }
        DailyActivity d;
        Map m;
        for (int i = 0; i < dList.size(); i++) {
            // 实例化a
            d = dList.get(i);
            // 实例化m
            m = new HashMap();
            // 使用m.put()方法在返回数据dataList的单位成员m中添加信息
            m.put("id", d.getId());
            m.put("studentNum",dailyActivityRepository.getStudentNum(d.getStudentName()));
            m.put("studentName",d.getStudentName());
            m.put("activityType",d.getActivityType());
            m.put("activityName",d.getActivityName());
            m.put("location",d.getLocation());
            m.put("time", DateTimeTool.parseDateTime(d.getTime(),"yyyy-MM-dd"));
            // 向dataList中添加m
            dataList.add(m);
        }
        // 返回dataList
        return dataList;
    }

    @PostMapping("/dailyActivityInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyActivityInit(@Valid @RequestBody DataRequest dataRequest){
        List dataList = getDailyActivityMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/dailyActivityEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyActivityEditInit(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        DailyActivity d = null;
        Optional<DailyActivity> op;
        if (id != null) {
            op = dailyActivityRepository.findById(id);
            if (op.isPresent()) {
                d = op.get();
            }
        }
        Map form = new HashMap();
        if (d!=null){
            form.put("studentName",d.getStudentName());
            form.put("activityType",d.getActivityType());
            form.put("activityName",d.getActivityName());
            form.put("location",d.getLocation());
            form.put("time",DateTimeTool.parseDateTime(d.getTime(),"yyyy-MM-dd"));
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("/dailyActivityDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyActivityDelete(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        DailyActivity d = null;
        Optional<DailyActivity> op;
        if (id != null) {
            op = dailyActivityRepository.findById(id);
            if (op.isPresent()) {
                d = op.get();
            }
        }
        if (d != null) {
            dailyActivityRepository.delete(d);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/dailyActivityEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse dailyActivityEditSubmit(@Valid @RequestBody DataRequest dataRequest){
        Map form = dataRequest.getMap("form");
        Integer id = CommonMethod.getInteger(form,"id");
        String studentName = CommonMethod.getString(form,"studentName");
        String activityType = CommonMethod.getString(form,"activityType");
        String activityName = CommonMethod.getString(form,"activityName");
        String location = CommonMethod.getString(form,"location");
        Date time = CommonMethod.getDate(form,"time");

        DailyActivity d = null;
        Optional<DailyActivity> op;

        if (id!=null){
            op = dailyActivityRepository.findById(id);
            if (op.isPresent()){
                d = op.get();
            }
        }
        if (d == null){
            d = new DailyActivity();
            id = dailyActivityRepository.getMaxId();
            if(id ==null){
                id = 1;
            } else {
                id +=1;
            }
            d.setId(id);
        }
        d.setStudentName(studentName);
        d.setActivityType(activityType);
        d.setActivityName(activityName);
        d.setLocation(location);
        d.setTime(time);
        dailyActivityRepository.save(d);
        return CommonMethod.getReturnData(d.getId());
    }
}
