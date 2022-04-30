package org.fatmansoft.teach.dto;

import lombok.*;

import java.util.Comparator;

/**
 * @author GuoSurui
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseRankDTO implements Comparable<CourseRankDTO> {
    private Integer rank;
    private Double percent;
    private Integer score;
    private Integer sameScoreNum;

    @Override
    public String toString() {
        return "CourseRankDTO{" +
                "rank=" + rank +
                ", percent=" + percent +
                ", sameScoreNum=" + sameScoreNum +
                '}';
    }

    @Override
    public int compareTo(CourseRankDTO o) {
        if (score < o.score) {
            return 1;
        } else if (score > o.score) {
            return -1;
        } else {
            return 0;
        }
    }
}

