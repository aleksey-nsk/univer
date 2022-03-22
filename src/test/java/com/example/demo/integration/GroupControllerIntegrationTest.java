package com.example.demo.integration;

import com.example.demo.dto.GroupDto;
import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Log4j2
public class GroupControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Успешный поиск первой страницы с группами")
    public void findFirstPageSuccess() throws Exception {
        Group group1 = saveGroupInDB();
        Group group2 = saveGroupInDB();

        // В группе1 будет два студента, в группе2 - ни одного
        Student student1 = saveStudentInDB(group1);
        Student student2 = saveStudentInDB(group1);

        assertThat(groupRepository.findAll().size()).isEqualTo(2);
        assertThat(studentRepository.findAll().size()).isEqualTo(2);

        mockMvc.perform(get(BASE_GROUP_URL + "?pageIndex=1"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content[0].id").value(group2.getId()))
                .andExpect(jsonPath("$.content[0].groupNumber").value(group2.getGroupNumber()))
                .andExpect(jsonPath("$.content[0].createdAt").isString())
                .andExpect(jsonPath("$.content[0].students").isEmpty())

                .andExpect(jsonPath("$.content[1].id").value(group1.getId()))
                .andExpect(jsonPath("$.content[1].groupNumber").value(group1.getGroupNumber()))
                .andExpect(jsonPath("$.content[1].createdAt").isString())
                .andExpect(jsonPath("$.content[1].students[0].id").value(student1.getId()))
                .andExpect(jsonPath("$.content[1].students[0].enrolmentDate").isString())
                .andExpect(jsonPath("$.content[1].students[0].lastname").value(student1.getLastname()))
                .andExpect(jsonPath("$.content[1].students[0].firstname").value(student1.getFirstname()))
                .andExpect(jsonPath("$.content[1].students[0].middlename").value(student1.getMiddlename()))
                .andExpect(jsonPath("$.content[1].students[1].id").value(student2.getId()))
                .andExpect(jsonPath("$.content[1].students[1].enrolmentDate").isString())
                .andExpect(jsonPath("$.content[1].students[1].lastname").value(student2.getLastname()))
                .andExpect(jsonPath("$.content[1].students[1].firstname").value(student2.getFirstname()))
                .andExpect(jsonPath("$.content[1].students[1].middlename").value(student2.getMiddlename()))

                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(8))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.size").value(8))
                .andExpect(jsonPath("$.empty").value("false"));

        assertThat(groupRepository.findAll().size()).isEqualTo(2);
        assertThat(studentRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Успешный поиск группы по id")
    public void findByIdSuccess() throws Exception {
        GroupDto groupDto = GroupDto.valueOf(saveGroupInDB());
        log.debug("groupDto: " + groupDto);

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);

        String expectedJson = objectMapper.writeValueAsString(groupDto);
        log.debug("expectedJson: " + expectedJson);

        mockMvc.perform(get(BASE_GROUP_URL + "/" + groupDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Группа по id не найдена")
    public void findByIdFail() throws Exception {
        mockMvc.perform(get(BASE_GROUP_URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(groupRepository.findAll().size()).isEqualTo(0);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Успешное добавление новой группы")
    public void saveSuccess() throws Exception {
        GroupDto groupDto = GroupDto.valueOf(createGroup());
        log.debug("groupDto: " + groupDto);

        String groupDtoAsJson = objectMapper.writeValueAsString(groupDto);
        log.debug("groupDtoAsJson: " + groupDtoAsJson);

        mockMvc.perform(post(BASE_GROUP_URL).content(groupDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.groupNumber").value(groupDto.getGroupNumber()))
                .andExpect(jsonPath("$.createdAt").isString())
                .andExpect(jsonPath("$.students").isEmpty());

        assertThat(groupRepository.findAll().size()).isEqualTo(1);
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }
}
