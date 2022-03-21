package com.example.demo.service;

import com.example.demo.dto.GroupDto;

import java.util.List;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface GroupService {

    /**
     * <p>Возвращает список групп</p>
     *
     * @return Список групп
     */
    List<GroupDto> findAll();

    /**
     * <p>Возвращает группу по идентификатору</p>
     *
     * @param id Идентификатор группы
     * @return Группа
     */
    GroupDto findById(Long id);

    /**
     * <p>Добавляет новую группу</p>
     *
     * @param groupDto Данные группы для добавления
     * @return Сохранённая в БД группа
     */
    GroupDto save(GroupDto groupDto);
}
