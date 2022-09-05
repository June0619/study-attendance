package com.spring.attandance.service;

import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    public Long join(Member member) {

        //1. 전화번호 중복 체크
        memberValidator.duplicateCheck(member);

        //2. 회원 DB 저장
        memberRepository.save(member);
        return member.getId();
    }
}
