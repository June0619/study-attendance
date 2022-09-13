package com.spring.attandance.config;

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
    public Member parse(String authorization) {

        //TODO: JWT 으로 수정
        Optional<Member> loginMember = memberRepository.findMemberByMobile(authorization);

        return loginMember.orElseThrow(() -> new RuntimeException("로그인이 필요합니다."));
    }
}
