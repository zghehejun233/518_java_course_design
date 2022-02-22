package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "person",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "perNum"),
        })
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

    public Person() {
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPerNum() {
        return perNum;
    }

    public void setPerNum(String perNum) {
        this.perNum = perNum;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getPerCard() {
        return perCard;
    }

    public void setPerCard(String perCard) {
        this.perCard = perCard;
    }

}
