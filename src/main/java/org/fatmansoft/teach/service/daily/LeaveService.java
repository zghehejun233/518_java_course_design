package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.daily.Leave;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class LeaveService {
    @Resource
    private LeaveImpl leave;


    public List<Object> getAllLeave(DataRequest dataRequest) {
        leave.setStudentId(dataRequest.getInteger("studentId"));
        return leave.queryAllLeave();
    }


    public Map<String, Object> getLeaveDetail(DataRequest dataRequest) {
        Integer leaveId = dataRequest.getInteger("id");
        return leave.queryLeaveDetail(leaveId);
    }

    public Integer saveLeave(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Leave leaveData = new Leave();
        leaveData.setLeaveId(CommonMethod.getInteger(form, "id"));
        leaveData.setReason(CommonMethod.getString(form, "reason"));
        leaveData.setState(CommonMethod.getInteger(form, "state"));
        leaveData.setType(CommonMethod.getInteger(form, "type"));
        leaveData.setTime(CommonMethod.getTime(form, "time"));
        return leave.insertLeave(leaveData);
    }

    public void deleteLeave(DataRequest dataRequest) {
        Integer leaveId = dataRequest.getInteger("id");
        leave.dropLeave(leaveId);
    }

}
