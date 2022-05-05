package org.fatmansoft.teach.dto;

import lombok.*;

/**
 * @author 16645
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TotalRankDTO {
    private Integer rank;
    private Double percent;
    private Double averageScore;
    private Integer sameScoreNum;

    @Override
    public String toString() {
        return "TotalRankDTO{" +
                "rank=" + rank +
                ", percent=" + percent +
                ", averageScore=" + averageScore +
                ", sameScoreNum=" + sameScoreNum +
                '}';
    }
}
