package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "Студенты")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить студента")
    public StudentDto save(@RequestBody @Valid StudentDto studentDto, @PathVariable("groupId") Long groupId) {
        return studentService.save(studentDto, groupId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить студента")
    public void delete(@PathVariable("id") Long id) {
        studentService.delete(id);
    }
}
