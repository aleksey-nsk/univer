package com.example.demo.unit.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import com.example.demo.service.impl.GroupServiceImpl;
import com.example.demo.service.impl.StudentServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest(classes = {GroupServiceImpl.class, StudentServiceImpl.class})
@ActiveProfiles("test")
@Log4j2
public abstract class BaseUnitTestForService {

    @Autowired
    protected GroupService groupService;

    @Autowired
    protected StudentService studentService;

    @MockBean
    protected GroupRepository groupRepository;

    @MockBean
    protected StudentRepository studentRepository;

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
