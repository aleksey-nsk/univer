package com.example.demo.unit.controller;

import com.example.demo.controller.GroupController;
import com.example.demo.controller.StudentController;
import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

@WebMvcTest(controllers = {GroupController.class, StudentController.class})
@ActiveProfiles("test")
@Log4j2
public abstract class BaseUnitTestForController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected GroupService groupService;

    @MockBean
    protected StudentService studentService;

    protected static final String BASE_GROUP_URL = "/api/v1/group";
    protected static final String BASE_STUDENT_URL = "/api/v1/student";

    protected Group createGroup(Long id) {
        Group group = new Group();
        group.setId(id);
        group.setGroupNumber(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
        group.setCreatedAt(LocalDateTime.now());
        group.setStudents(Collections.emptyList());

        log.debug("group: " + group);
        return group;
    }

    protected Student createStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setEnrolmentDate(LocalDateTime.now());
        student.setLastname(RandomStringUtils.randomAlphabetic(10));
        student.setFirstname(RandomStringUtils.randomAlphabetic(8));
        student.setMiddlename(RandomStringUtils.randomAlphabetic(6));

        log.debug("student: " + student);
        return student;
    }
}
