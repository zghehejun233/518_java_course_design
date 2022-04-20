package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class ScoreService {
    @Resource
    private ScoreImpl score;

    public List<Object> getAllScore(DataRequest dataRequest) {
        score.setStudentId(dataRequest.getInteger("studentId"));
        return score.queryAllScore();
    }

    public Map<String, Object> getScoreDetail(DataRequest dataRequest) {
        Integer scoreId = dataRequest.getInteger("id");
        return score.queryScoreDetail(scoreId);
    }

    public Integer saveScore(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Score scoreData = new Score();
        String courseName = CommonMethod.getString(form, "courseName");
        String studentName = CommonMethod.getString(form, "studentName");
        scoreData.setScore(CommonMethod.getInteger(form, "score"));
        scoreData.setMethod(CommonMethod.getString(form, "method"));
        scoreData.setDailyScore(CommonMethod.getInteger(form, "dailyScore"));
        scoreData.setExamScore(CommonMethod.getInteger(form, "examScore"));
        return score.insertScore(scoreData, courseName, studentName);
    }

    public void deleteScore(DataRequest dataRequest) {
        Integer scoreId = dataRequest.getInteger("id");
        score.dropScore(scoreId);
    }
}
