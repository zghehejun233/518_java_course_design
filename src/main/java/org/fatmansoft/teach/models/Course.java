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

    @NotBlank
    @Size(min = 1,max = 5)
    private String teacherName;

    @NotBlank
    private String classroom;

    // 一周要上好几次课呀。。
    // 这可怎么设计
    // 回头看看i山大咋写的。。
    private Date time;
}
