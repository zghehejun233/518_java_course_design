package org.fatmansoft.teach.models.academic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * @author GuoSurui
 */
@Entity
@Table(name = "course_time",uniqueConstraints = {@UniqueConstraint(columnNames = "course_time_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseTime {
    @Id
    @Column(name = "course_time_id")
    private Integer courseTimeId;
    @Column(name = "day")
    private Integer day;
    @Column(name = "period")
    private Integer period;

    @ManyToMany
    @JoinTable(
            name = "course_time_course",
            joinColumns = @JoinColumn(name = "course_time_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CourseTime that = (CourseTime) o;
        return courseTimeId != null && Objects.equals(courseTimeId, that.courseTimeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CourseTime{" +
                "courseTimeId=" + courseTimeId +
                ", day=" + day +
                ", period=" + period +
                ", courses=" + courses +
                '}';
    }
}
