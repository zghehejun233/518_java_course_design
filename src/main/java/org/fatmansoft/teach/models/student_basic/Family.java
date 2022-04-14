package org.fatmansoft.teach.models.student_basic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author guosurui
 */
@Entity
@Table(name = "family",uniqueConstraints = {@UniqueConstraint(columnNames = "family_id")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Family {
    @Id
    @Column(name = "family_id")
    private Integer familyId;
    @Column(name = "name")
    private String name;
    @Column(name = "relation")
    private String relation;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private Integer age;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;
}
