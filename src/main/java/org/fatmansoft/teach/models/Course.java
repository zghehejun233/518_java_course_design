package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course",
        uniqueConstraints = {
        })
@Setter
@Getter
public class Course {
    @Id
    private Integer id;
    @NotBlank
    @Size(max = 20)
    private String courseNum;

    @Size(max = 50)
    private String courseName;

    private String teacherName;
    private String classroom;
    private Date time;
}
