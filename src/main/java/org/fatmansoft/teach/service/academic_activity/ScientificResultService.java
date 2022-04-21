package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.academic_activity.ScientificResult;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.service.academic.ScoreImpl;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ScientificResultService {
    @Resource
    private ScientificResultImpl scientificResult;

    public List<Object> getAllScientificResult(DataRequest dataRequest) {
        scientificResult.setStudentId(dataRequest.getInteger("studentId"));
        return scientificResult.queryAllScientificResult();
    }

    public Map<String,Object> getScientificResult(DataRequest dataRequest) {
        Integer scientificResultId = dataRequest.getInteger("id");
        return scientificResult.queryScientificResult(scientificResultId);
    }

    public Integer saveScientificResult(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        ScientificResult scientificResultData = new ScientificResult();
        scientificResultData.setScientificResultId(CommonMethod.getInteger(form,"id"));
        scientificResultData.setName(CommonMethod.getString(form,"name"));
        scientificResultData.setContent(CommonMethod.getString(form,"content"));
        scientificResultData.setAuthor(CommonMethod.getString(form,"author"));
        scientificResultData.setLevel(CommonMethod.getString(form,"level"));
        scientificResultData.setTime(CommonMethod.getTime(form,"time"));

        return scientificResult.insertScientificResult(scientificResultData);
    }

    public void deleteScientificResult(DataRequest dataRequest) {
        Integer scientificResultId = dataRequest.getInteger("id");
        scientificResult.dropScientificResult(scientificResultId);
    }
}
