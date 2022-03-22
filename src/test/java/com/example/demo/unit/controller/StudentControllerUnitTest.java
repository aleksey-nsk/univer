package com.example.demo.unit.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.exception.StudentNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class StudentControllerUnitTest extends BaseUnitTestForController {

    @Test
    @DisplayName("Успешное добавление нового студента")
    public void saveSuccess() throws Exception {
        Long groupId = 1L;
        Long studentId = 10L;

        StudentDto saved = StudentDto.valueOf(createStudent(studentId));

        StudentDto forSaving = new StudentDto();
        forSaving.setLastname(saved.getLastname());
        forSaving.setFirstname(saved.getFirstname());
        forSaving.setMiddlename(saved.getMiddlename());
        log.debug("forSaving: " + forSaving);

        String forSavingAsJson = objectMapper.writeValueAsString(forSaving);
        String savedAsJson = objectMapper.writeValueAsString(saved);
        log.debug("forSavingAsJson: " + forSavingAsJson);
        log.debug("savedAsJson: " + savedAsJson);

        Mockito.when(studentService.save(forSaving, groupId))
                .thenReturn(saved);

        mockMvc.perform(post(BASE_STUDENT_URL + "/" + groupId).content(forSavingAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(savedAsJson, true));
    }

    @Test
    @DisplayName("Студент для удаления не найден")
    public void deleteFail() throws Exception {
        Long id = 1L;

        Mockito.doThrow(new StudentNotFoundException(id))
                .when(studentService).delete(id);

        mockMvc.perform(delete(BASE_STUDENT_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
