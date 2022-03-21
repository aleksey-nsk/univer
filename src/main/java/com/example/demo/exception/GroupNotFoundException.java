package com.example.demo.exception;

public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(Long id) {
        super("Не найдена группа по id=" + id);
    }
}
