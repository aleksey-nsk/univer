package com.example.demo.service;

import com.example.demo.dto.StudentDto;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface StudentService {

    /**
     * <p>Добавляет нового студента</p>
     *
     * @param studentDto Данные студента для добавления
     * @param groupId    Идентификатор группы
     * @return Сохранённый в БД студент
     */
    StudentDto save(StudentDto studentDto, Long groupId);

    /**
     * <p>Удаляет студента из БД</p>
     *
     * @param id Идентификатор студента
     */
    void delete(Long id);
}
