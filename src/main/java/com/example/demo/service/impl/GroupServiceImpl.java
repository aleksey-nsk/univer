package com.example.demo.service.impl;

import com.example.demo.dto.GroupDto;
import com.example.demo.entity.Group;
import com.example.demo.entity.comparator.GroupComparator;
import com.example.demo.exception.GroupDuplicateException;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupDto> findAll() {
        List<GroupDto> list = groupRepository.findAll()
                .stream()
                .sorted(new GroupComparator())
                .map(it -> GroupDto.valueOf(it))
                .collect(Collectors.toList());

        log.debug("Список всех групп: " + list);
        return list;
    }

    @Override
    public GroupDto findById(Long id) {
        GroupDto groupDto = groupRepository.findById(id)
                .map(it -> GroupDto.valueOf(it))
                .orElseThrow(() -> new GroupNotFoundException(id));

        log.debug("По id=" + id + " получена группа: " + groupDto);
        return groupDto;
    }

    @Override
    public GroupDto save(GroupDto groupDto) {
        log.debug("Сохранить новую группу в БД");
        log.debug("  groupDto: " + groupDto);

        if (groupRepository.findByGroupNumber(groupDto.getGroupNumber()) != null) {
            throw new GroupDuplicateException("В БД уже есть группа с номером '" + groupDto.getGroupNumber() + "'");
        }

        Group group = groupDto.mapToGroup();
        group.setCreatedAt(LocalDateTime.now());
        log.debug("  группа для сохранения в БД: " + group);

        Group saved = groupRepository.save(group);
        log.debug("  в БД сохранена группа: " + saved);

        return GroupDto.valueOf(saved);
    }
}
