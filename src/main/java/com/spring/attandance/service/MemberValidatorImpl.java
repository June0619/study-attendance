package com.spring.attandance.service;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.repository.MemberQueryRepository;
import com.spring.attandance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberValidatorImpl implements MemberValidator {

    private final MemberQueryRepository repository;

    public void duplicateCheck(Member member) {

        MemberSearchCondition condition = MemberSearchCondition.builder()
                .mobile(member.getMobile())
                .build();

        // 1. Mobile
        repository.findOne(condition)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
