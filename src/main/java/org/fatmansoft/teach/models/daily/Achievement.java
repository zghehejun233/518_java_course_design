package org.fatmansoft.teach.models.daily;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fatmansoft.teach.models.student_basic.Student;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

// 本model用于需求（4）学生荣誉信息管理，包括获得的各种称号奖励等的实现
// 以下称呼本类为成就，包含的荣誉信息有荣誉称号、奖学金等等

@Entity
@Table(name = "achievement",
        uniqueConstraints = {@UniqueConstraint(columnNames = "achievement_id")
        })
@Setter
@Getter
@ToString
public class Achievement {
    @Id
    @Column(name = "achievement_id")
    private Integer achievementId;

    @Column(name = "name")
    private String name;
    @Column(name = "level")
    private String level;
    @Column(name = "type")
    private String type;
    @Column(name = "content")
    private String content;
    @Column(name = "organization")
    private String organization;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
