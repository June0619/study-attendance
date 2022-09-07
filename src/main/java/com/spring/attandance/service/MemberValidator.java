package com.spring.attandance.service;

import com.spring.attandance.domain.Member;

public interface MemberValidator {

    // 회원 중복 검사
    void duplicateCheck(Member member);

    // 회원 오픈 예정 스터디 검사
    void openStudyCheck(Long id);

    // 스터디 그룹 소유자 검사
    void studyGroupOwnerCheck(Long id);
}
