package org.fatmansoft.teach.models.academic;

import lombok.*;
import org.fatmansoft.teach.models.student_basic.Student;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "checkout", uniqueConstraints = {@UniqueConstraint(columnNames = "checkout_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Checkout {
    @Id
    @Column(name = "checkout_id")
    private Integer checkoutId;
    @Column(name = "method")
    private String method;
    @Column(name = "state")
    private Integer state;
    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Checkout checkout = (Checkout) o;
        return checkoutId != null && Objects.equals(checkoutId, checkout.checkoutId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "checkoutId=" + checkoutId +
                ", method='" + method + '\'' +
                ", state=" + state +
                ", time=" + time +
                '}';
    }
}