package org.fatmansoft.teach.models.academic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "course_selection",uniqueConstraints = {@UniqueConstraint(columnNames = "course_selection_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSelection {
    @Id
    @Column(name = "course_selection_id")
    private Integer courseSelectionId;
    @Column(name = "type")
    private  String type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CourseSelection that = (CourseSelection) o;
        return courseSelectionId != null && Objects.equals(courseSelectionId, that.courseSelectionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CourseSelection{" +
                "courseSelectionId=" + courseSelectionId +
                ", type='" + type + '\'' +
                ", course=" + course +
                ", student=" + student +
                '}';
    }
}
