package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "daily_activity",
        uniqueConstraints = {
        })
@Setter
@Getter
public class DailyActivity {
    @Id
    private Integer id;

    private String studentName;
    private String activityType;
    private String activityName;
    private String location;
    private Date time;
}
