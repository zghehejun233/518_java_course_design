package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Leave;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.LeaveRepository;
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
public class LeaveController {
    @Autowired
    private LeaveRepository leaveRepository;

    public List getLeaveMapList(String numName) {
        List dataList = new ArrayList();
        List<Leave> lList = leaveRepository.findAll();
        if (lList == null || lList.size() == 0) {
            return dataList;
        }
        Leave l;
        Map m;
        for (int i = 0; i < lList.size(); i++) {
            // 实例化a
            l = lList.get(i);
            // 实例化m
            m = new HashMap();
            // 使用m.put()方法在返回数据dataList的单位成员m中添加信息
            m.put("id", l.getId());
            m.put("studentNum", leaveRepository.getStudentNum(l.getStudentName()));
            m.put("studentName", l.getStudentName());
            m.put("reason", l.getReason());
            m.put("startTime", DateTimeTool.parseDateTime(l.getStartTime(), "yyyy-MM-dd"));
            m.put("endTime", DateTimeTool.parseDateTime(l.getEndTime(), "yyyy-MM-dd"));
            // 向dataList中添加m
            dataList.add(m);
        }
        // 返回dataList
        return dataList;
    }

    @PostMapping("/leaveInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = getLeaveMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/leaveEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        Leave l = null;
        Optional<Leave> op;
        if (id != null) {
            op = leaveRepository.findById(id);
            if (op.isPresent()) {
                l = op.get();
            }
        }
        Map form = new HashMap();
        if (l != null) {
            form.put("studentName", l.getStudentName());
            form.put("reason", l.getReason());
            form.put("startTime", DateTimeTool.parseDateTime(l.getStartTime(), "yyyy-MM-dd"));
            form.put("endTime", DateTimeTool.parseDateTime(l.getEndTime(), "yyyy-MM-dd"));
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("leaveDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveDelete(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        Leave l = null;
        Optional<Leave> op;
        if (id != null) {
            op = leaveRepository.findById(id);
            if (op.isPresent()) {
                l = op.get();
            }
        }
        if (l != null) {
            leaveRepository.delete(l);
        }
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("leaveEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse leaveEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer id = CommonMethod.getInteger(form, "id");
        String studentName = CommonMethod.getString(form, "studentName");
        String reason = CommonMethod.getString(form, "reason");
        Date startTime = CommonMethod.getDate(form, "startTime");
        Date endTime = CommonMethod.getDate(form, "endTime");

        Leave l = null;
        Optional<Leave> op;

        if (id != null) {
            op = leaveRepository.findById(id);
            if (op.isPresent()) {
                l = op.get();
            }
        }
        if (l == null) {
            l = new Leave();
            id = leaveRepository.getMaxId();
            if (id == null) {
                id = 1;
            } else {
                id += 1;
            }
            l.setId(id);
        }
        l.setStudentName(studentName);
        l.setReason(reason);
        l.setStartTime(startTime);
        l.setEndTime(endTime);
        leaveRepository.save(l);
        return CommonMethod.getReturnData(l.getId());
    }


}
