package com.spring.attandance.controller.dto.member;

import com.spring.attandance.controller.dto.group_member.GroupMemberResponseDTO;
import com.spring.attandance.domain.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberResponseDTO {

    private String name;
    private String email;
    private String mobile;

    List<GroupMemberResponseDTO> belongGroups = new ArrayList<>();

    public MemberResponseDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.mobile = member.getMobile();
        member.getGroupMembers().forEach(groupMember -> {
            belongGroups.add(new GroupMemberResponseDTO(groupMember));
        });
    }
}
