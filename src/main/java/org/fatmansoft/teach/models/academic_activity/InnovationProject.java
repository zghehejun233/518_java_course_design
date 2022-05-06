package org.fatmansoft.teach.models.academic_activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "innovation_project", uniqueConstraints = {@UniqueConstraint(columnNames = "innovation_project_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InnovationProject {
    @Id
    @Column(name = "innovation_project_id")
    private Integer innovationProjectId;
    @Column(name = "content")
    private String content;
    @Column(name = "principal")
    private String principal;
    @Column(name = "advisor")
    private String advisor;
    @Column(name = "organization")
    private String organization;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InnovationProject that = (InnovationProject) o;
        return innovationProjectId != null && Objects.equals(innovationProjectId, that.innovationProjectId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
