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
    private Integer id;

    @NotBlank
    @Size(max = 20)
    private String studentNum;

    @Size(max = 50)
    private String studentName;
    @Size(max = 2)
    private String sex;
    private Integer age;
    private Date birthday;

    @NotBlank(message = "手机号码不能为空", groups = {Insert.class})
    @NotNull(message = "手机号不能为空", groups = {Insert.class})
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4 ,5, 6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phoneNumber;

}
