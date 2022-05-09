package org.fatmansoft.teach.models.student_basic;

import lombok.*;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.TestMethodOrder;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author guosurui
 */
@Entity
@Table(name = "social_relation", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"social_relation_id"})
})

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SocialRelation {
    @Id
    @Column(name = "social_relation_id")
    private Integer socialRelationId;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SocialRelation that = (SocialRelation) o;
        return socialRelationId != null && Objects.equals(socialRelationId, that.socialRelationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
