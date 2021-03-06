package org.fatmansoft.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

/**
 * @author 16645
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseScoresDTO {
    private String course;

    private Integer courseId;
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
