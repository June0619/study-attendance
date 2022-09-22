package com.spring.attandance.controller.dto.group_member;

import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.enums.GroupRole;
import lombok.Data;

@Data
public class GroupMemberResponseDTO {
    private String groupName;
    private String memberName;
    private GroupRole role;

    public GroupMemberResponseDTO(GroupMember groupMember) {
        this.groupName = groupMember.getGroup().getName();
        this.memberName = groupMember.getMember().getName();
        this.role = groupMember.getRole();
    }
}
