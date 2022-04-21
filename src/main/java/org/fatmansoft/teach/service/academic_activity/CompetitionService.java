package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.models.academic_activity.Competition;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 16645
 */
@Service
public class CompetitionService {
    @Resource
    private CompetitionImpl competition;

    public List<Object> getAllCompetition(DataRequest dataRequest) {
        competition.setStudentId(dataRequest.getInteger("studentId"));
        return competition.queryAllPractice();
    }

    public Map<String, Object> getCompetitionDetail(DataRequest dataRequest) {
        Integer competitionId = dataRequest.getInteger("id");
        return competition.queryCompetition(competitionId);
    }

    public Integer saveCompetition(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Competition competitionData = new Competition();
        competitionData.setCompetitionId(CommonMethod.getInteger(form, "id"));
        competitionData.setName(CommonMethod.getString(form, "name"));
        competitionData.setType(CommonMethod.getString(form, "type"));
        competitionData.setOrganizer(CommonMethod.getString(form, "organizer"));
        competitionData.setAward(CommonMethod.getString(form, "award"));
        competitionData.setTime(CommonMethod.getTime(form, "time"));
        return competition.insertCompetition(competitionData);
    }

    public void deleteCompetition(DataRequest dataRequest) {
        Integer competitionId = dataRequest.getInteger("id");
        competition.dropPractice(competitionId);
    }
}
