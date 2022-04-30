package org.fatmansoft.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author GuoSurui
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRankDTO {
    private Integer rank;
    private Double percent;
    private Integer sameScoreNum;
}
