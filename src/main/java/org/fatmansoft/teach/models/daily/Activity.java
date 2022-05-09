package org.fatmansoft.teach.models.daily;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "activity",
        uniqueConstraints = {@UniqueConstraint(columnNames = "activity_id")
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Activity {
    @Id
    @Column(name = "activity_id")
    private Integer activityId;
    @Column(name = "name")
    private String name;
    @Column(name = "principal")
    private String principal;
    @Column(name = "content")
    private String content;
    @Column(name = "location")
    private String location;
    @Column(name = "time")
    private Date time;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
