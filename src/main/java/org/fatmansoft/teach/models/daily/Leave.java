package org.fatmansoft.teach.models.daily;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave", uniqueConstraints = {@UniqueConstraint(columnNames = "leave_id")})
@Setter
@Getter
@ToString
public class Leave {
    @Id
    @Column(name = "leave_id")
    private Integer leaveId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "leave_state")
    private Integer state;
    @Column(name = "leave_type")
    private Integer type;
    @Column(name = "leave_time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
