package org.fatmansoft.teach.service.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.daily.Cost;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.daily.ActivityRepository;
import org.fatmansoft.teach.repository.daily.CostRepository;
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
public class CostImpl {
    @Resource
    private CostRepository costRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllCost() {
        List<Object> result = new ArrayList<>();
        List<Cost> costList = costRepository.findCostsByStudent_StudentId(studentId);
        if (costList.size() == 0) {
            return result;
        }
        Cost cost;
        Map<String, Object> tempMap;
        for (Cost value : costList) {
            cost = value;
            tempMap = new HashMap<>();
            tempMap.put("id",cost.getCostId());
            tempMap.put("amount",cost.getAmount());
            tempMap.put("description",cost.getDescription());
            tempMap.put("time",cost.getTime());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String,Object> queryCostDetail(Integer costId) {
        Cost cost = getCost(costId);
        Map<String,Object> resultMap = new HashMap<>();
        if (cost!=null){
            resultMap.put("id",cost.getCostId());
            resultMap.put("amount",cost.getAmount());
            resultMap.put("description",cost.getDescription());
            resultMap.put("time",cost.getTime());
        }
        return resultMap;
    }

    public Integer insertCost(Cost costData) {
        Cost cost = getCost(costData.getCostId());
        Integer maxCostId = null;
        if (cost == null) {
            cost = new Cost();
            maxCostId = costRepository.getMaxId();
            if (maxCostId == null) {
                maxCostId = 1;
            } else {
                maxCostId += 1;
            }
            cost.setCostId(maxCostId);
        }
        cost.setAmount(costData.getAmount());
        cost.setDescription(costData.getDescription());
        cost.setTime(costData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            cost.setStudent(relatedStudent);
        }
        costRepository.save(cost);
        return maxCostId;
    }

    public void dropCost(Integer costId) {
        Cost cost = getCost(costId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getCosts().remove(cost);
        }
        costRepository.delete(cost);
    }

    private Cost getCost(Integer costId) {
        Cost cost = null;
        Optional<Cost> op;
        if (costId != null) {
            op = costRepository.findById(costId);
            if (op.isPresent()) {
                cost = op.get();
            }
        }
        return cost;
    }

}
