package org.fatmansoft.teach.models.academic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "attendance",uniqueConstraints = {})
@Setter
@Getter
public class Checkout {
    @Id
    private Integer id;
    private String studentName;
    private String courseName;
    private Boolean checkState;
    private Date time;

}