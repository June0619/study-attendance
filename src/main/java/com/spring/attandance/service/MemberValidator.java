package com.spring.attandance.service;

import com.spring.attandance.domain.Member;

public interface MemberValidator {

    // 회원 중복 검사
    // 1. Mobile
    void duplicateCheck(Member member);
}
