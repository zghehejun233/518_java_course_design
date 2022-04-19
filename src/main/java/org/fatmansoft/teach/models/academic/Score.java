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
    private Integer id;
    private String studentName;
    private Integer score;
    private Float GPA;
    private Integer rank;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Score score = (Score) o;
        return id != null && Objects.equals(id, score.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
