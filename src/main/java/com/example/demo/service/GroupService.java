package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import org.springframework.data.domain.Page;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface GroupService {

    /**
     * <p>Возвращает страницу с группами</p>
     *
     * @param pageIndex Номер страницы
     * @return Страница с группами
     */
    Page<GroupDto> findPage(Integer pageIndex);

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
