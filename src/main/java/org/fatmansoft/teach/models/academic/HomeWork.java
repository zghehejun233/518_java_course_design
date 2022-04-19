package org.fatmansoft.teach.models.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "home_work",uniqueConstraints = {})
@Setter
@Getter
public class HomeWork {
    @Id
    private Integer id;
    private String studentName;
    private String courseName;
    private String done;
    private String right;
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HomeWork homeWork = (HomeWork) o;
        return id != null && Objects.equals(id, homeWork.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
