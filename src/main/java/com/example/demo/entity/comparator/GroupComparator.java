package com.example.demo.entity.comparator;

import com.example.demo.entity.Group;

import java.util.Comparator;

public class GroupComparator implements Comparator<Group> {

    @Override
    public int compare(Group o1, Group o2) {
        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
    }
}
