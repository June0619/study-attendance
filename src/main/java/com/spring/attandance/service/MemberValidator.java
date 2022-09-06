package com.spring.attandance.service;

import com.spring.attandance.domain.Member;

public interface MemberValidator {

    // 회원 중복 검사
    void duplicateCheck(Member member);

    // 회원 오픈 예정 스터디 검사
    static void openStudyCheck(Member member) {
        // TODO
    }

    // 회원 스터디 소유 검사
    static void studyOwnerCheck(Member member) {
        // TODO
    }
}
