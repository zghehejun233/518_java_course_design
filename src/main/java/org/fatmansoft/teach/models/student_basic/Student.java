package org.fatmansoft.teach.models.student_basic;

import lombok.*;
import org.fatmansoft.teach.models.academic.Checkout;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.HomeWork;
import org.fatmansoft.teach.models.academic.Score;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author guosurui
 */
@Entity
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "student_num"})
})

@NoArgsConstructor
@Getter
@Setter
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
    private Integer sex;
    @Column(name = "age")
    private Integer age;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Family> families;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<SocialRelation> socialRelations;

    @OneToMany(mappedBy = "student",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<EducationExperience> educationExperiences;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<CourseSelection> courseSelections;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Checkout> checkouts;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<HomeWork> homeWork;
    
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Score> scores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return studentId != null && Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentNum='" + studentNum + '\'' +
                ", studentName='" + studentName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
