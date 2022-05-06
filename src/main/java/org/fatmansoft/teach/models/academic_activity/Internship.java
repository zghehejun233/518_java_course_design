package org.fatmansoft.teach.models.academic_activity;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "internship",
        uniqueConstraints = {@UniqueConstraint(columnNames = "internship_id")
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Internship {
    @Id
    @Column(name = "internship_id")
    private Integer internshipId;
    @Column(name = "orgazization")
    private String organization;
    @Column(name = "content")
    private String content;
    @Column(name = "location")
    private String location;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
