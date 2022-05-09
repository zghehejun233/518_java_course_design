package org.fatmansoft.teach.models.academic_activity;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "competition", uniqueConstraints = {@UniqueConstraint(columnNames = "competition_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Competition {
    @Id
    @Column(name = "competition_id")
    private Integer competitionId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "organizer")
    private String organizer;
    @Column(name = "award")
    private String award;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Competition that = (Competition) o;
        return competitionId != null && Objects.equals(competitionId, that.competitionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
