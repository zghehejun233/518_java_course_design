package org.fatmansoft.teach.models.academic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reference",uniqueConstraints = {})
@Setter
@Getter
public class Reference {
    @Id
    private Integer id;

    private String referenceType;
    private String referenceName;
    private String referenceLink;
}
