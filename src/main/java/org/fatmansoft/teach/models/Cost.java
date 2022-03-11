package org.fatmansoft.teach.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "cost",uniqueConstraints = {})
@Setter
@Getter
public class Cost {
    @Id
    private Integer id;
    private String costName;
    private String costMoney;
    private Date time;
}
