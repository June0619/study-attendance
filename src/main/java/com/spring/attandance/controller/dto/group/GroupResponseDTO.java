package com.spring.attandance.controller.dto.group;

import com.spring.attandance.controller.dto.group_member.GroupMemberResponseDTO;
import com.spring.attandance.domain.Group;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupResponseDTO {

    private long id;
    private String name;
    private List<GroupMemberResponseDTO> belongMembers = new ArrayList<>();
    private int memberCount;

    public GroupResponseDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        group.getGroupMembers().forEach(groupMember -> {
            belongMembers.add(new GroupMemberResponseDTO(groupMember));
        });
        this.memberCount = belongMembers.size();
    }
}
