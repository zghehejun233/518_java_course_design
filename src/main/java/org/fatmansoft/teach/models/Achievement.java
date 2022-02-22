package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(	name = "achievement",
        uniqueConstraints = {
        })
public class Achievement {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name="studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name="courseId")
    private Student course;

    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getCourse() {
        return course;
    }

    public void setCourse(Student course) {
        this.course = course;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
