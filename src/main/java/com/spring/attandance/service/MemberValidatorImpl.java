package com.spring.attandance.service;

import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.domain.cond.StudySearchCondition;
import com.spring.attandance.domain.enums.PassedStudy;
import com.spring.attandance.repository.query.MemberQueryRepository;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.repository.query.StudyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberValidatorImpl implements MemberValidator {

    private final MemberQueryRepository memberQueryRepository;
    private final GroupRepository groupRepository;
    private final StudyQueryRepository studyQueryRepository;

    @Override
    public void duplicateCheck(Member member) {

        MemberSearchCondition condition = MemberSearchCondition.builder()
                .mobile(member.getMobile())
                .build();

        // 1. Mobile
        memberQueryRepository.searchMember(condition)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Override
    public void openStudyCheck(Long id) {
        StudySearchCondition cond = StudySearchCondition.builder()
                .ownerId(id)
                .passedStudy(PassedStudy.NOT_PASSED)
                .build();

        List<Study> result = studyQueryRepository.searchStudy(cond);

        if (result.size() > 0) {
            throw new IllegalStateException("진행 중이거나 진행 예정인 스터디가 존재합니다.");
        }
    }

    @Override
    public void groupOwnerCheck(Long id) {

        int count = groupRepository.countGroupsByMasterId(id);

        if (count > 0) {
            throw new IllegalStateException("소유 중인 스터디 그룹이 존재합니다.");
        }
    }
}
