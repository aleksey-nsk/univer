package com.example.demo.unit.controller;

import com.example.demo.dto.GroupDto;
import com.example.demo.exception.GroupNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class GroupControllerUnitTest extends BaseUnitTestForController {

    @Test
    @DisplayName("Успешный поиск группы по id")
    public void findByIdSuccess() throws Exception {
        Long id = 1L;
        GroupDto groupDto = GroupDto.valueOf(createGroup(id));

        String expectedJson = objectMapper.writeValueAsString(groupDto);
        log.debug("expectedJson: " + expectedJson);

        Mockito.doReturn(groupDto)
                .when(groupService).findById(id);

        mockMvc.perform(get(BASE_GROUP_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    @DisplayName("Группа по id не найдена")
    public void findByIdFail() throws Exception {
        Long id = 1L;

        Mockito.doThrow(new GroupNotFoundException(id))
                .when(groupService).findById(id);

        mockMvc.perform(get(BASE_GROUP_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное добавление новой группы")
    public void saveSuccess() throws Exception {
        GroupDto saved = GroupDto.valueOf(createGroup(1L));

        GroupDto forSaving = new GroupDto();
        forSaving.setGroupNumber(saved.getGroupNumber());
        log.debug("forSaving: " + forSaving);

        String forSavingAsJson = objectMapper.writeValueAsString(forSaving);
        String savedAsJson = objectMapper.writeValueAsString(saved);
        log.debug("forSavingAsJson: " + forSavingAsJson);
        log.debug("savedAsJson: " + savedAsJson);

        Mockito.when(groupService.save(forSaving))
                .thenReturn(saved);

        mockMvc.perform(post(BASE_GROUP_URL).content(forSavingAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(savedAsJson, true));
    }
}
