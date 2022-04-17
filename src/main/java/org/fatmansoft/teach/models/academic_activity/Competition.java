package org.fatmansoft.teach.models.academic_activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "competition", uniqueConstraints = {@UniqueConstraint(columnNames = "competition_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
