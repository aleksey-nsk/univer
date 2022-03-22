package com.example.demo.unit.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.StudentNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Log4j2
public class StudentServiceUnitTest extends BaseUnitTestForService {

    @Test
    @DisplayName("Новый студент не добавлен (группа не найдена)")
    public void saveFail() {
        Long groupId = 10L;

        Student student = createStudent(1L);

        StudentDto studentDto = new StudentDto();
        studentDto.setLastname(student.getLastname());
        studentDto.setFirstname(student.getFirstname());
        studentDto.setMiddlename(student.getMiddlename());
        log.debug("studentDto: " + studentDto);

        Mockito.doReturn(Optional.empty())
                .when(groupRepository).findById(groupId);

        try {
            studentService.save(studentDto, groupId);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (GroupNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(groupId.toString());
        }
    }

    @Test
    @DisplayName("Студент для удаления не найден")
    public void deleteFail() {
        Long id = 1L;

        Mockito.doThrow(new StudentNotFoundException(id))
                .when(studentRepository).findById(id);

        try {
            studentService.delete(id);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (StudentNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(id.toString());
        }
    }
}
