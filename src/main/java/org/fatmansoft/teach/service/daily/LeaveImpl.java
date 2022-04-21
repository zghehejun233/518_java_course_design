package org.fatmansoft.teach.service.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.daily.Cost;
import org.fatmansoft.teach.models.daily.Leave;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.daily.LeaveRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class LeaveImpl {
    @Resource
    private LeaveRepository leaveRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllLeave() {
        List<Object> result = new ArrayList<>();
        List<Leave> leaveList = leaveRepository.findLeavesByStudent_StudentId(studentId);
        if (leaveList.size() == 0) {
            return result;
        }
        Leave leave;
        Map<String, Object> tempMap;
        for (Leave value : leaveList) {
            leave = value;
            tempMap = new HashMap<>();
            tempMap.put("id",leave.getLeaveId());
            tempMap.put("reason",leave.getReason());
            tempMap.put("state",leave.getState());
            tempMap.put("type",leave.getType());
            tempMap.put("time",leave.getTime());
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryLeaveDetail(Integer leaveId) {
       Leave leave = getLeave(leaveId);
        Map<String, Object> resultMap = new HashMap<>();
        if (leave != null) {
            resultMap.put("id",leave.getLeaveId());
            resultMap.put("reason",leave.getReason());
            resultMap.put("state",leave.getState());
            resultMap.put("type",leave.getType());
            resultMap.put("time",leave.getTime());
        }
        return resultMap;
    }


    public Integer insertLeave(Leave leaveData) {
        Leave leave = getLeave(leaveData.getLeaveId());
        Integer maxLeaveId = null;
        if (leave == null) {
            leave = new Leave();
            maxLeaveId = leaveRepository.getMaxId();
            if (maxLeaveId == null) {
                maxLeaveId = 1;
            } else {
                maxLeaveId += 1;
            }
            leave.setLeaveId(maxLeaveId);
        }

        leave.setReason(leaveData.getReason());
        leave.setState(leaveData.getState());
        leave.setType(leaveData.getType());
        leave.setTime(leaveData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            leave.setStudent(relatedStudent);
        }
        leaveRepository.save(leave);
        return maxLeaveId;
    }

    public void dropLeave(Integer leaveId) {
        Leave leave = getLeave(leaveId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getLeaves().remove(leave);
        }
        leaveRepository.delete(leave);
    }

    private Leave getLeave(Integer leaveId) {
        Leave leave = null;
        Optional<Leave> op;
        if (leave != null) {
            op = leaveRepository.findById(leaveId);
            if (op.isPresent()) {
                leave = op.get();
            }
        }
        return leave;
    }
}
