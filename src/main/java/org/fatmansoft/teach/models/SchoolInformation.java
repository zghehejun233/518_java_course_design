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
@Table(name = "school_information", uniqueConstraints = {

})
@Setter
@Getter
public class SchoolInformation {
    @Id
    private Integer id;

    @NotBlank
    private String studentName;
    @NotBlank
    private String schoolName;
    private String location;
    private String headTeacherName;
    @NotBlank(message = "手机号码不能为空", groups = {Insert.class})
    @NotNull(message = "手机号不能为空", groups = {Insert.class})
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4 ,5, 6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String headTeacherPhone;
    private Date startTime;
    private Date endTime;


}
