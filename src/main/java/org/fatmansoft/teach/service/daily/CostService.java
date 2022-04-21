package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.models.daily.Cost;
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
public class CostService {
    @Resource
    private CostImpl cost;

    public List<Object> getAllCost(DataRequest dataRequest) {
        cost.setStudentId(dataRequest.getInteger("studentId"));
        return cost.queryAllCost();
    }

    public Map<String, Object> getCostDetail(DataRequest dataRequest) {
        Integer costId = dataRequest.getInteger("id");
        return cost.queryCostDetail(costId);
    }

    public Integer saveCost(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Cost costData = new Cost();
        costData.setCostId(CommonMethod.getInteger(form, "id"));
        costData.setAmount(CommonMethod.getString(form, "amount"));
        costData.setDescription(CommonMethod.getString(form, "description"));
        costData.setTime(CommonMethod.getTime(form, "time"));
        return cost.insertCost(costData);
    }

    public void deleteCost(DataRequest dataRequest) {
        Integer costId = dataRequest.getInteger("id");
        cost.dropCost(costId);
    }
}
