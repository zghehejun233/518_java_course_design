package org.fatmansoft.teach.models.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "person",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "perNum"),
        })
@Setter
@Getter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    @NotBlank
    @Size(max = 20)
    private String perNum;

    @NotBlank
    @Size(max = 50)
    private String perName;

    @Size(max = 2)
    private String perType;

    @Size(max = 20)
    private String perCard;

    @Size(max = 20)
    private String phone;

    @Size(max = 60)
    @Email
    private String email;


}
