package org.fatmansoft.teach.models.student_basic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

import java.util.Date;
import java.util.Set;

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

}
