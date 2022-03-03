package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Setter;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.relational.core.sql.Insert;

import java.util.Date;

@Entity
@Table(name = "society_information",uniqueConstraints = {})
public class SocietyInformation {
    @Id
    private Integer id;

    private String studentName;
    private Integer militaryService;
}
