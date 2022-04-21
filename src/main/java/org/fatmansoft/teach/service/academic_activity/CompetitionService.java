package org.fatmansoft.teach.service.academic_activity;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 16645
 */
@Service
public class CompetitionService {
    @Resource
    private CompetitionImpl competition;

    public List<Object> getAllCompetition(DataRequest dataRequest) {
        competition.setStudentId(dataRequest.getInteger("id"));
        return competition.queryAllPractice();
    }
}
