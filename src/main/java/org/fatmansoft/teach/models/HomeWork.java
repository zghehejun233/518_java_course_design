package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "home_work",uniqueConstraints = {})
@Setter
@Getter
public class HomeWork {
    @Id
    private Integer id;
    private String studentName;
    private String courseName;
    private String done;
    private String right;
    private Integer score;
}
