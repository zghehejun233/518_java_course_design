package org.fatmansoft.teach.models.academic_activity;


import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scientific_result",uniqueConstraints = {@UniqueConstraint(columnNames = "scientific_result_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScientificResult {
    @Id
    @Column(name = "scientific_result_id")
    private Integer scientificResultId;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "author")
    private String author;
    @Column(name = "level")
    private String level;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
