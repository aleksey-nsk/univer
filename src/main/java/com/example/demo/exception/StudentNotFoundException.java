package com.example.demo.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Не найден студент по id=" + id);
    }
}
