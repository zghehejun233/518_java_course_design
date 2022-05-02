package org.fatmansoft.teach.models.academic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "num"})
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "num")
    private String num;
    @Column(name = "name")
    private String name;
    @Column(name = "teacher")
    private String teacher;
    @Column(name = "credit")
    private Double credit;
    @Column(name = "start")
    private Integer start;
    @Column(name = "end")
    private Integer end;
    @Column(name = "recycle")
    private Integer recycle;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CourseSelection> courseSelections;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reference> references;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Checkout> checkouts;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<HomeWork> homeWork;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Score> scores;

    @ManyToMany(mappedBy = "courses",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<CourseTime> courseTimes;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return courseId != null && Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credit=" + credit +
                ", start=" + start +
                ", end=" + end +
                ", recycle=" + recycle +
                '}';
    }
}
