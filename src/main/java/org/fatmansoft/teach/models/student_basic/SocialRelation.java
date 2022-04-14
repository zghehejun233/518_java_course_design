package org.fatmansoft.teach.models.student_basic;

import lombok.*;

import javax.persistence.*;

/**
 * @author guosurui
 */
@Entity
@Table(name = "social_relation",uniqueConstraints = {@UniqueConstraint(columnNames = "social_relation_id")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialRelation {
    @Id
    @Column(name = "social_relation_id")
    private Integer socialRelationId;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;
}
