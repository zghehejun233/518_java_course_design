package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Competition;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.repository.academic_activity.CompetitionRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Setter
@Getter
public class CompetitionImpl {
    @Resource
    private CompetitionRepository competitionRepository;

    @Resource
    private Integer studentId;

    public List<Object> queryAllPractice() {
        List<Object> result = new ArrayList<>();
        List<Competition> competitionList = competitionRepository.findCompetitionsByStudent_StudentId(studentId);
        if (competitionList.size() == 0) {
            return result;
        }
        Competition competition;
        Map<String, Object> tempMap;
        for (Competition value : competitionList) {
            competition = value;
            tempMap = new HashMap<>();
            tempMap.put("id", competition.getCompetitionId());
            tempMap.put("name", competition.getName());
            tempMap.put("type", competition.getType());
            tempMap.put("organizer", competition.getOrganizer());
            tempMap.put("award", competition.getAward());
            tempMap.put("time", DateTimeTool.parseDateTime(competition.getTime(), "yyyy-MM-dd"));
            result.add(tempMap);
        }
        return result;
    }
}
