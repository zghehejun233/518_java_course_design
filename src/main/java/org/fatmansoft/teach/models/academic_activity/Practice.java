package org.fatmansoft.teach.models.academic_activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "practice",
        uniqueConstraints = {@UniqueConstraint(columnNames = "practice_id")
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Practice {
    @Id
    @Column(name = "practice_id")
    private Integer practiceId;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "principal")
    private String principal;
    @Column(name = "organization")
    private String organization;
    @Column(name = "location")
    private String location;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Practice practice = (Practice) o;
        return practiceId != null && Objects.equals(practiceId, practice.practiceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
