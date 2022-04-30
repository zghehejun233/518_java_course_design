package org.fatmansoft.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 16645
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentScoresDTO {
    private String course;
    private Integer score;
    private Double credit;
    private String type;

    private CourseRankDTO courseRankDTO;

    @Override
    public String toString() {
        return "StudentScoresDTO{" +
                "course='" + course + '\'' +
                ", score=" + score +
                ", credit=" + credit +
                ", type='" + type + '\'' +
                '}';
    }
}
