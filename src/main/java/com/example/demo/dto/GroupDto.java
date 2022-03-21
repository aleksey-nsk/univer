package com.example.demo.dto;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String groupNumber;

    private LocalDateTime createdAt;
    private List<Student> students;

    public static GroupDto valueOf(Group group) {
        return new GroupDto(
                group.getId(),
                group.getGroupNumber(),
                group.getCreatedAt(),
                group.getStudents()
        );
    }

    public Group mapToGroup() {
        return new Group(id, groupNumber, createdAt, students);
    }
}
