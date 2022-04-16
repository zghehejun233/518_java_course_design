package org.fatmansoft.teach.models.academic_activity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.fatmansoft.teach.models.student_basic.Student;

import java.util.Date;

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
}
