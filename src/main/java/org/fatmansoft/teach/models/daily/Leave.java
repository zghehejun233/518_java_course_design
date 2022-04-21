package org.fatmansoft.teach.models.daily;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave", uniqueConstraints = {@UniqueConstraint(columnNames = "leave_id")})
@Setter
@Getter
public class Leave {
    @Id
    @Column(name = "leave_id")
    private Integer leaveId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "state")
    private Integer state;
    @Column(name = "type")
    private Date type;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
