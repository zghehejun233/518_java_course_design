package org.fatmansoft.teach.models.daily;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cost", uniqueConstraints = {@UniqueConstraint(columnNames = "cost_id")})
@Setter
@Getter
@ToString
public class Cost {
    @Id
    @Column(name = "cost_id")
    private Integer costId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "amount")
    private String amount;
    @Column(name = "description")
    private String description;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
