package org.fatmansoft.teach.models.daily;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "outing", uniqueConstraints = {@UniqueConstraint(columnNames = "outing_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Outing {
    @Id
    @Column(name = "outing_id")
    private Integer outingId;
    @Column(name = "outing_name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "location")
    private String location;
    @Column(name = "outing_time")
    private Date time;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
