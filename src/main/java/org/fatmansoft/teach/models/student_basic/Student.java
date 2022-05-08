package org.fatmansoft.teach.models.student_basic;

import lombok.*;
import org.fatmansoft.teach.models.academic.Checkout;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.HomeWork;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.academic_activity.*;
import org.fatmansoft.teach.models.daily.*;
import org.fatmansoft.teach.service.academic_activity.InnovationProjectImpl;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "学号不能为空！")
    private String studentNum;
    @Column(name = "student_name")
    @NotBlank(message = "姓名不能为空！")
    private String studentName;
    @Column(name = "sex")
    @Range(min = 1, max = 3, message = "性别的选择是有限的！")
    private Integer sex;
    @Column(name = "age")
    @Min(value = 0, message = "年龄为非负整数！")
    private Integer age;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "school")
    private String school;
    @Column(name = "major")
    private String major;
    @Column(name = "class_name")
    private String className;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Family> families;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<SocialRelation> socialRelations;

    @OneToMany(mappedBy = "student",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<EducationExperience> educationExperiences;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CourseSelection> courseSelections;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Checkout> checkouts;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<HomeWork> homeWork;
    
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.EAGER)
    private Set<Score> scores;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Practice> practices;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Competition> competitions;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<InnovationProject> innovationProjects;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Internship> internships;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Lecture> lectures;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<ScientificResult> scientificResults;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Activity> activities;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Outing> outings;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Leave> leaves;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Cost> costs;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private Set<Achievement> achievements;

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
