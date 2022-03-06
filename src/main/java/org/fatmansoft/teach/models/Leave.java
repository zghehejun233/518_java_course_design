package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "leave",uniqueConstraints = {})
@Setter
@Getter
public class Leave {
    @Id
    private Integer id;

    private String studentName;
    private String reason;
    private Date startTime;
    private Date endTime;
}
