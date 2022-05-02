package org.fatmansoft.teach.service.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.daily.Outing;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.daily.OutingRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class OutingImpl {
    @Resource
    private OutingRepository outingRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllOuting() {
        List<Object> result = new ArrayList<>();
        List<Outing> outingList = outingRepository.findOutingsByStudent_StudentId(studentId);
        if (outingList.size() == 0) {
            return result;
        }
        Outing outing;
        Map<String, Object> tempMap;
        for (Outing value : outingList) {
            outing = value;
            tempMap = new HashMap<>();
            tempMap.put("id", outing.getOutingId());
            tempMap.put("name", outing.getName());
            tempMap.put("content", outing.getContent());
            tempMap.put("studentName",outing.getStudent().getStudentName());
            tempMap.put("location", outing.getLocation());
            tempMap.put("time", DateTimeTool.parseDateTime(outing.getTime(),"yyyy-MM-dd"));
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryOutingDetail(Integer outingId) {
        Outing outing = getOuting(outingId);
        Map<String, Object> resultMap = new HashMap<>();
        if (outing != null) {
            resultMap.put("id", outing.getOutingId());
            resultMap.put("name", outing.getName());
            resultMap.put("studentName",outing.getStudent().getStudentName());
            resultMap.put("content", outing.getContent());
            resultMap.put("location", outing.getLocation());
            resultMap.put("time", outing.getTime());
        }else {
            Optional<Student> op = studentRepository.findById(studentId);
            op.ifPresent(student -> resultMap.put("studentName",student.getStudentName()));
        }
        return resultMap;
    }

    public Integer insertOuting(Outing outingData) {
        Outing outing = getOuting(outingData.getOutingId());
        Integer maxOutingId = null;
        if (outing == null) {
            outing = new Outing();
            maxOutingId = outingRepository.getMaxId();
            if (maxOutingId == null) {
                maxOutingId = 1;
            } else {
                maxOutingId += 1;
            }
            outing.setOutingId(maxOutingId);
        }
        outing.setName(outingData.getName());
        outing.setContent(outingData.getContent());
        outing.setLocation(outingData.getLocation());
        outing.setTime(outingData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            outing.setStudent(relatedStudent);
        }
        outingRepository.save(outing);
        return maxOutingId;
    }

    public void dropOuting(Integer outingId) {
        Outing outing = getOuting(outingId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getOutings().remove(outing);
        }
        outingRepository.delete(outing);
    }

    private Outing getOuting(Integer outingId) {
        Outing outing = null;
        Optional<Outing> op;
        if (outingId != null) {
            op = outingRepository.findById(outingId);
            if (op.isPresent()) {
                outing = op.get();
            }
        }
        return outing;
    }
}
