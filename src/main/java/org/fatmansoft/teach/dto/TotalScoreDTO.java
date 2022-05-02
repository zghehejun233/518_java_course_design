package org.fatmansoft.teach.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TotalScoreDTO {
    private Double averageScore;
    private Double averageGPA;

    private TotalRankDTO totalRankDTO;

    @Override
    public String toString() {
        return "TotalScoreDTO{" +
                "averageScore=" + averageScore +
                ", averageGPA=" + averageGPA +
                ", totalRankDTO=" + totalRankDTO +
                '}';
    }
}
