package org.fatmansoft.teach.models.student_basic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.relational.core.sql.Insert;

import java.util.Date;
import java.util.Set;

/**
 * @author guosurui
 */
@Entity
@Table(name = "student",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","student_num"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "student_num")
    private String studentNum;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private Integer age;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REFRESH)
    private Set<Family> families;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REFRESH)
    private Set<SocialRelation> socialRelations;

    @OneToMany(mappedBy = "student",cascade =CascadeType.MERGE)
    private Set<EducationExperience> educationExperiences;
}