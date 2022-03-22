package com.example.demo.integration;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class StudentControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Успешное добавление нового студента")
    public void saveSuccess() throws Exception {
        Group group = saveGroupInDB();

        StudentDto studentDto = StudentDto.valueOf(createStudent());
        log.debug("studentDto: " + studentDto);

        String studentDtoAsJson = objectMapper.writeValueAsString(studentDto);
        log.debug("studentDtoAsJson: " + studentDtoAsJson);

        mockMvc.perform(post(BASE_STUDENT_URL + "/" + group.getId()).content(studentDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.enrolmentDate").isString())
                .andExpect(jsonPath("$.lastname").value(studentDto.getLastname()))
                .andExpect(jsonPath("$.firstname").value(studentDto.getFirstname()))
                .andExpect(jsonPath("$.middlename").value(studentDto.getMiddlename()));

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Новый студент не добавлен (группа не найдена)")
    public void saveFail() throws Exception {
        StudentDto studentDto = StudentDto.valueOf(createStudent());
        log.debug("studentDto: " + studentDto);

        String studentDtoAsJson = objectMapper.writeValueAsString(studentDto);
        log.debug("studentDtoAsJson: " + studentDtoAsJson);

        mockMvc.perform(post(BASE_STUDENT_URL + "/1").content(studentDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(groupRepository.findAll().size()).isEqualTo(0);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Успешное удаление студента")
    public void deleteSuccess() throws Exception {
        Group group = saveGroupInDB();
        Student student1 = saveStudentInDB(group);
        Student student2 = saveStudentInDB(group);

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(2);

        mockMvc.perform(delete(BASE_STUDENT_URL + "/" + student1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Студент для удаления не найден")
    public void deleteFail() throws Exception {
        mockMvc.perform(delete(BASE_STUDENT_URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(groupRepository.findAll().size()).isEqualTo(0);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }
}
