package com.spring.attandance.service;

import com.spring.attandance.controller.dto.member.MemberUpdateDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.MemberQueryRepository;
import com.spring.attandance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public Long join(Member member) {

        //1. 회원 중복 Validation
        memberValidator.duplicateCheck(member);

        //2. 회원 DB 저장
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updateMember(MemberUpdateDTO dto) {

        //1. 회원 정보 조회
        Member member = memberRepository.findById(dto.getId()).get();

        //2. 회원 정보 수정
        member.update(dto);
        return member.getId();
    }

    @Transactional
    public void deleteMember(Long id) {



        memberRepository.deleteById(id);
    }
}
