package com.spring.attandance.controller.dto.member;

import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.enums.GroupRole;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class LoginMemberDTO {

    private Long id;
    private String name;
    private String mobile;
    private String email;
    private boolean isGuest;
    private List<GroupMemberInnerDTO> groups = new ArrayList<>();

    public static LoginMemberDTO of(Member member) {
        LoginMemberDTO dto = new LoginMemberDTO();
        dto.id = member.getId();
        dto.name = member.getName();
        dto.mobile = member.getMobile();
        dto.email = member.getEmail();
        dto.isGuest = false;

        for (GroupMember groupMember : member.getGroupMembers()) {
            GroupMemberInnerDTO innerDTO = new GroupMemberInnerDTO();
            innerDTO.setId(groupMember.getId());
            innerDTO.setName(groupMember.getGroup().getName());
            innerDTO.setRole(groupMember.getRole());
            dto.groups.add(innerDTO);
        }

        return dto;
    }

    public LoginMemberDTO() {
        name = "Guest";
        isGuest = true;
    }

    @Data
    @ToString
    static class GroupMemberInnerDTO {
        private Long id;
        private String name;
        private GroupRole role;
    }


}
