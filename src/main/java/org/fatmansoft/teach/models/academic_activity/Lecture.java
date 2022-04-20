package org.fatmansoft.teach.models.academic_activity;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lecture", uniqueConstraints = {@UniqueConstraint(columnNames = "lecture_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @Column(name = "lecture_id")
    private Integer lectureId;
    @Column(name = "theme")
    private String theme;
    @Column(name = "presenter")
    private String presenter;
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
