package org.fatmansoft.teach.models;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
