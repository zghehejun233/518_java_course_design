package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

// 本model用于需求（4）学生荣誉信息管理，包括获得的各种称号奖励等的实现
// 以下称呼本类为成就，包含的荣誉信息有荣誉称号、奖学金等等

@Entity
@Table(name = "achievement",
        uniqueConstraints = {
        })
@Setter
@Getter
public class Achievement {
    @Id
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String studentName;

    private String achievementName;
    private String achievementType;
    private String organization;
    private String level;
    private Date time;
}
