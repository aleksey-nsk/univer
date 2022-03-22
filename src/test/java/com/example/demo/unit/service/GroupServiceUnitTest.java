package com.example.demo.unit.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.entity.Group;
import com.example.demo.exception.GroupDuplicateException;
import com.example.demo.exception.GroupNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Log4j2
public class GroupServiceUnitTest extends BaseUnitTestForService {

    @Test
    @DisplayName("Группа по id не найдена")
    public void findByIdFail() {
        Long id = 1L;

        Mockito.doReturn(Optional.empty())
                .when(groupRepository).findById(id);

        try {
            groupService.findById(id);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (GroupNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(id.toString());
        }
    }

    @Test
    @DisplayName("Дубликат группы по номеру не добавлен")
    public void saveFail() {
        Group exist = createGroup(1L);
        String groupNumber = exist.getGroupNumber();

        GroupDto forSaving = new GroupDto();
        forSaving.setGroupNumber(groupNumber);
        log.debug("forSaving: " + forSaving);

        Mockito.when(groupRepository.findByGroupNumber(groupNumber))
                .thenReturn(exist);
        try {
            groupService.save(forSaving);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (GroupDuplicateException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(groupNumber);
        }
    }
}
