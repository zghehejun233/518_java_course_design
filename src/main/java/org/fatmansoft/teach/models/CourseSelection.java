package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_selection")
@Setter
@Getter
public class CourseSelection {
    @Id
    private Integer id;

    private String studentName;
    private String courseName;
    private String referenceBook;
    private String referenceMaterial;
}
