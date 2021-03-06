package org.fatmansoft.teach.models.student_basic;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author guosurui
 */
@Entity
@Table(name = "family", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"family_id"})
})

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Family {
    @Id
    @Column(name = "family_id")
    private Integer familyId;
    @Column(name = "name")
    @NotBlank(message = "不能没有名字！")
    @Length(max = 18,message = "名字太长！")
    private String name;
    @Column(name = "relation")
    @NotBlank(message = "什么关系！")
    private String relation;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private Integer age;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Family family = (Family) o;
        return familyId != null && Objects.equals(familyId, family.familyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "[姓名：" + name +
                ", 关系：" + relation +
                ", 年龄：" + age+"]";
    }
}
