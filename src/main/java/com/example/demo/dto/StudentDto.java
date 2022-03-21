package com.example.demo.dto;

import com.example.demo.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    private Date enrolmentDate;
    private String lastname;
    private String firstname;
    private String middlename;

    public static StudentDto valueOf(Student student) {
        return new StudentDto(
                student.getId(),
                student.getEnrolmentDate(),
                student.getLastname(),
                student.getFirstname(),
                student.getMiddlename()
        );
    }

    public Student mapToStudent() {
        Student student = new Student();
        student.setId(id);
        student.setEnrolmentDate(enrolmentDate);
        student.setLastname(lastname);
        student.setFirstname(firstname);
        student.setMiddlename(middlename);
        return student;
    }
}
