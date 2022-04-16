package org.fatmansoft.teach.models.student_basic;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author guosurui
 */
@Entity
@Table(name = "education_experience", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"education_experience_id"})
})

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EducationExperience {
    @Id
    @Column(name = "education_experience_id")
    private Integer educationExperienceId;
    @Column(name = "school_name")
    private String schoolName;
    @Column(name = "level")
    private String level;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EducationExperience that = (EducationExperience) o;
        return educationExperienceId != null && Objects.equals(educationExperienceId, that.educationExperienceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
