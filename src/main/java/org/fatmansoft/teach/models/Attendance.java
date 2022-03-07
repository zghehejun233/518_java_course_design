package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "attendance")
@Setter
@Getter
public class Attendance {
    @Id
    private Integer id;
    private String studentName;
    private String courseName;
    private Date time;
    private Boolean check;
}
