package org.fatmansoft.teach.models.academic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "homework",uniqueConstraints = {@UniqueConstraint(columnNames = "homework_id")})
@Setter
@Getter
@ToString
public class HomeWork {
    @Id
    @Column(name = "homework_id")
    private Integer homeworkId;
    @Column(name = "content")
    private String content;
    @Column(name = "score")
    private Integer score;

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
        HomeWork homeWork = (HomeWork) o;
        return homeworkId != null && Objects.equals(homeworkId, homeWork.homeworkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
