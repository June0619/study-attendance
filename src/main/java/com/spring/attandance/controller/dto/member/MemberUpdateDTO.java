package com.spring.attandance.controller.dto.member;

import lombok.Data;

@Data
public class MemberUpdateDTO {

    private Long id;
    private String name;
    private String email;
}