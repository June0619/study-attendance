package com.spring.attandance.config;

import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class AuthorizationParserImpl implements AuthorizationParser {

    private final MemberRepository memberRepository;

    @Override
    public LoginMemberDTO parse(String authorization) {

        //TODO: 인가 (Authorization) 로직 추가
        Optional<Member> loginMember = memberRepository.findMemberByMobile(authorization);
        return loginMember.map(LoginMemberDTO::of).orElse(new LoginMemberDTO());
    }
}
