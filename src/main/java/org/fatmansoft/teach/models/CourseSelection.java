package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "course_selection")
@Setter
@Getter
public class CourseSelection {
    @Id
    private Integer id;

    @NotBlank
    private String studentName;
    @NotBlank
    private String courseName;
}
