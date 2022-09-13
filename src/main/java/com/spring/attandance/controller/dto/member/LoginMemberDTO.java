package com.spring.attandance.controller.dto.member;

import com.spring.attandance.domain.Member;
import lombok.Data;
import lombok.Getter;

@Getter
public class LoginMemberDTO {

    private Long id;
    private String name;
    private String mobile;
    private String email;

    public static LoginMemberDTO of(Member member) {
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO();
        loginMemberDTO.id = member.getId();
        loginMemberDTO.name = member.getName();
        loginMemberDTO.mobile = member.getMobile();
        loginMemberDTO.email = member.getEmail();
        return loginMemberDTO;
    }


}
