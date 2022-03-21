package com.example.demo.dto;

import com.example.demo.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    private LocalDateTime enrolmentDate;

    @NotNull
    @NotEmpty
    private String lastname;

    @NotNull
    @NotEmpty
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
