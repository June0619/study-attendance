package com.spring.attandance.service;

import com.spring.attandance.controller.dto.member.MemberUpdateDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.query.MemberQueryRepository;
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

        //1. 스터디 그룹 소유 validation
        memberValidator.studyGroupOwnerCheck(id);
        //2. 오픈 예정 스터디 validation
        memberValidator.openStudyCheck(id);
        //3. 회원 삭제
        memberRepository.deleteById(id);
    }
}
