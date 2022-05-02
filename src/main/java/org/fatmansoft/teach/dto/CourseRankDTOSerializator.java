package org.fatmansoft.teach.dto;

import org.fatmansoft.teach.models.academic.Score;

/**
 * @author 16645
 */
public class CourseRankDTOSerializator {
    public static String serializeCourseRankDTO(CourseRankDTO courseRankDTO) {

        String result = courseRankDTO.getScore() + ":";
        result = result + courseRankDTO.getRank() + ":";
        result = result + courseRankDTO.getPercent() + ":";
        result = result + courseRankDTO.getSameScoreNum();
        return result;
    }

    public static CourseRankDTO deserializeCourseRankDTO(String str) {
        CourseRankDTO courseRankDTO = new CourseRankDTO();
        String stringDelimiter = ":";
        String[] temp = str.split(stringDelimiter);
        courseRankDTO.setScore(Integer.parseInt(temp[0]));
        courseRankDTO.setRank(Integer.parseInt(temp[1]));
        courseRankDTO.setPercent(Double.parseDouble(temp[2]));
        courseRankDTO.setSameScoreNum(Integer.parseInt(temp[3]));
        return courseRankDTO;
    }
}
