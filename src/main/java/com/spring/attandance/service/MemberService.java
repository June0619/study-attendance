package com.spring.attandance.service;

import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {



        memberRepository.save(member);
        return member.getId();
    }
}
