package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "attendance",uniqueConstraints = {})
@Setter
@Getter
public class Attendance {
    @Id
    private Integer id;
    @NotBlank
    private String studentName;
    private String courseName;
    private Date time;
    private String check;
}
