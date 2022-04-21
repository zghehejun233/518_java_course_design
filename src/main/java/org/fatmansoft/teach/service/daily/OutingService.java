package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.daily.Outing;
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
public class OutingService {
    @Resource
    private OutingImpl outing;


    public List<Object> getAllOuting(DataRequest dataRequest) {
        outing.setStudentId(dataRequest.getInteger("studentId"));
        return outing.queryAllOuting();
    }


    public Map<String, Object> getOutingDetail(DataRequest dataRequest) {
        Integer outingId = dataRequest.getInteger("id");
        return outing.queryOutingDetail(outingId);
    }

    public Integer saveOuting(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Outing outingData = new Outing();
        outingData.setOutingId(CommonMethod.getInteger(form,"id"));
        outingData.setName(CommonMethod.getString(form,"name"));
        outingData.setContent(CommonMethod.getString(form,"content"));
        outingData.setLocation(CommonMethod.getString(form,"location"));
        outingData.setTime(CommonMethod.getTime(form,"time"));
        return outing.insertOuting(outingData);
    }

    public void deleteOuting(DataRequest dataRequest) {
        Integer outingId = dataRequest.getInteger("id");
        outing.dropOuting(outingId);
    }
}
