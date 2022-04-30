package org.fatmansoft.teach.models.academic;


import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "score",uniqueConstraints = {})
@Setter
@Getter
public class Score {
    @Id
    @Column(name = "score_id")
    private Integer scoreId;
    @Column(name = "score")
    private Integer score;
    @Column(name = "method")
    private String method;
    @Column(name = "daily_score")
    private Integer dailyScore;
    @Column(name = "exam_score")
    private Integer examScore;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Score score = (Score) o;
        return scoreId != null && Objects.equals(scoreId, score.scoreId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", score=" + score +
                ", method='" + method + '\'' +
                ", dailyScore=" + dailyScore +
                ", examScore=" + examScore +
                '}';
    }
}
