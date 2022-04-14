package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Setter;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.relational.core.sql.Insert;

import java.util.Date;

@Entity
@Table(name = "student",
        uniqueConstraints = {
        })
@Setter
@Getter
public class Student {
    @Id
    private Integer studentId;
    private String studentNum;
    private String studentName;
    private String sex;
    private Integer age;
    private String phoneNumber;
    private Date birthday;
}
