package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "enrolment_date")
    private Date enrolmentDate;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    // Игнорировать поле group в JSON иначе будет ошибка: "Infinite recursion (StackOverflowError)"
    @JsonIgnore
    public Group getGroup() {
        return group;
    }

    // Не выводить поле group иначе будет ошибка: "StackOverflowError"
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", enrolmentDate=" + enrolmentDate +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                '}';
    }
}
