package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(name = "user_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserType name;

    public UserType() {

    }

    public UserType(EUserType name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EUserType getName() {
        return name;
    }

    public void setName(EUserType name) {
        this.name = name;
    }
}