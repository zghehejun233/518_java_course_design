package org.fatmansoft.teach.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "score",uniqueConstraints = {})
@Setter
@Getter
public class Score {
    @Id
    private Integer id;
    private String studentName;
    private Integer score;
    private Float GPA;
    private Integer rank;
}
