package com.example.demo.integration;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Log4j2
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected GroupRepository groupRepository;

    @Autowired
    protected StudentRepository studentRepository;

    protected static final String BASE_GROUP_URL = "/api/v1/group";
    protected static final String BASE_STUDENT_URL = "/api/v1/student";

    @AfterEach
    protected void tearDown() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();
    }

    protected Group saveGroupInDB() {
        String groupNumber = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        LocalDateTime createdAt = LocalDateTime.now();
        List<Student> students = Collections.emptyList();

        Group group = new Group();
        group.setGroupNumber(groupNumber);
        group.setCreatedAt(createdAt);
        group.setStudents(students);
        log.debug("group: " + group);

        Group savedGroup = groupRepository.save(group);
        log.debug("savedGroup: " + savedGroup);
        return savedGroup;
    }

    protected Group createGroup() {
        String groupNumber = RandomStringUtils.randomAlphanumeric(10).toUpperCase();

        Group group = new Group();
        group.setGroupNumber(groupNumber);

        log.debug("group: " + group);
        return group;
    }

    protected Student saveStudentInDB(Group group) {
        LocalDateTime enrolmentDate = LocalDateTime.now();
        String lastname = RandomStringUtils.randomAlphabetic(10);
        String firstname = RandomStringUtils.randomAlphabetic(8);
        String middlename = RandomStringUtils.randomAlphabetic(6);

        Student student = new Student();
        student.setEnrolmentDate(enrolmentDate);
        student.setLastname(lastname);
        student.setFirstname(firstname);
        student.setMiddlename(middlename);
        student.setGroup(group);
        log.debug("student: " + student);

        Student savedStudent = studentRepository.save(student);
        log.debug("savedStudent: " + savedStudent);
        return savedStudent;
    }

    protected Student createStudent() {
        String lastname = RandomStringUtils.randomAlphabetic(10);
        String firstname = RandomStringUtils.randomAlphabetic(8);
        String middlename = RandomStringUtils.randomAlphabetic(6);

        Student student = new Student();
        student.setLastname(lastname);
        student.setFirstname(firstname);
        student.setMiddlename(middlename);

        log.debug("student: " + student);
        return student;
    }
}
