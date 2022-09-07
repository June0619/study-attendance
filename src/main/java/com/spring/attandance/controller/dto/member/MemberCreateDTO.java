package com.spring.attandance.controller.dto.member;

import com.spring.attandance.domain.Member;
import lombok.Data;

@Data
public class MemberCreateDTO {


    private String name;
    private String mobile;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .mobile(mobile)
                .email(email)
                .build();
    }
}
