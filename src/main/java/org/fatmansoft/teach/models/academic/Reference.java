package org.fatmansoft.teach.models.academic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reference", uniqueConstraints = {@UniqueConstraint(columnNames = "reference_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reference {
    @Id
    @Column(name = "reference_id")
    private Integer referenceId;
    @Column(name = "type")
    private String type;
    @Column(name = "language")
    private String language;
    @Column(name = "author")
    private String author;
    @Column(name = "detail")
    private String detail;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reference reference = (Reference) o;
        return referenceId != null && Objects.equals(referenceId, reference.referenceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Reference{" +
                "referenceId=" + referenceId +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", author='" + author + '\'' +
                ", detail='" + detail + '\'' +
                ", course=" + course +
                '}';
    }
}
