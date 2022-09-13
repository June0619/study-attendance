package com.spring.attandance.config;

import com.spring.attandance.controller.dto.member.LoginMemberDTO;

import java.util.Optional;

public interface AuthorizationParser {

    LoginMemberDTO parse(String authorization);
}
