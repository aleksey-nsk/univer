package com.example.demo.controller;

import com.example.demo.dto.GroupDto;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GroupDto> findPage(@RequestParam("pageIndex") Integer pageIndex) {
        return groupService.findPage(pageIndex);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDto findById(@PathVariable("id") Long id) {
        return groupService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto save(@RequestBody @Valid GroupDto groupDto) {
        return groupService.save(groupDto);
    }
}
