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
    private final boolean isGuest;
    private List<GroupMemberInnerDTO> groups = new ArrayList<>();

    public LoginMemberDTO (Member member) {
        id = member.getId();
        name = member.getName();
        mobile = member.getMobile();
        email = member.getEmail();
        isGuest = false;

        for (GroupMember groupMember : member.getGroupMembers()) {
            GroupMemberInnerDTO innerDTO = new GroupMemberInnerDTO();
            innerDTO.setGroupId(groupMember.getGroup().getId());
            innerDTO.setName(groupMember.getGroup().getName());
            innerDTO.setRole(groupMember.getRole());
            groups.add(innerDTO);
        }
    }

    public LoginMemberDTO() {
        name = "Guest";
        isGuest = true;
    }

    @Data
    @ToString
    public static class GroupMemberInnerDTO {
        private Long groupId;
        private String name;
        private GroupRole role;
    }


}
