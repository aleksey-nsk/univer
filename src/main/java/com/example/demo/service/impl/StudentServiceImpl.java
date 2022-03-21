package com.example.demo.service.impl;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public StudentDto save(StudentDto studentDto, Long groupId) {
        log.debug("Сохранить нового студента в БД");
        log.debug("  studentDto: " + studentDto);
        log.debug("  groupId: " + groupId);

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        log.debug("  group: " + group);

        Student student = studentDto.mapToStudent();
        student.setEnrolmentDate(new Date());
        student.setGroup(group);
        log.debug("  студент для сохранения в БД: " + student);

        Student saved = studentRepository.save(student);
        log.debug("  в БД сохранён студент: " + saved);

        return StudentDto.valueOf(saved);
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        log.debug("Удалить студента: " + student);
        studentRepository.deleteById(id);
    }
}
