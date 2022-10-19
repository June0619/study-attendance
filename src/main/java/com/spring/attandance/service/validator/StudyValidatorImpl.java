package com.spring.attandance.service.validator;

import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.StudyMember;
import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.domain.enums.StudyStatus;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.StudyMemberRepository;
import com.spring.attandance.repository.StudyRepository;
import com.spring.attandance.repository.query.StudyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spring.attandance.domain.enums.GroupRole.ADMIN;
import static com.spring.attandance.domain.enums.GroupRole.MASTER;

@Component
@RequiredArgsConstructor
public class StudyValidatorImpl implements StudyValidator {

    private final GroupMemberRepository groupMemberRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    @Override
    public void isStudyGroupAdmin(Long memberId, Long studyId) {
        //Study Group Admin Check
        groupMemberRepository.findByMemberIdAndGroupId(memberId, studyId)
                .filter(groupMember -> groupMember.getRole().equals(ADMIN) || groupMember.getRole().equals(MASTER))
                .orElseThrow(() -> new IllegalStateException("스터디 그룹 관리자가 아닙니다."));
    }

    @Override
    public void isStudyOwner(Long memberId, Long studyId) {
        //Study Owner Check
        studyRepository.findById(studyId)
                .filter(study -> study.getOwner().getId().equals(memberId))
                .orElseThrow(() -> new IllegalStateException("스터디 소유자가 아닙니다."));
    }

    @Override
    public void isStudyGroupMember(Long memberId, Long studyId) {
        //Study Group Member Check
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByMemberIdAndGroupId(memberId, studyId);
        GroupMember groupMember = groupMemberOptional
                .orElseThrow(() -> new IllegalStateException("스터디 그룹에 가입되어 있지 않습니다."));

        if (groupMember.getRole().equals(GroupRole.WAIT)) {
            throw new IllegalStateException("가입 승인을 대기중인 회원 입니다.");
        }
    }

    @Override
    public void periodCheck(Long memberId, Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(() -> new IllegalStateException("스터디가 존재하지 않습니다."));

        //Ongoing Study Check
        List<Study> openStudyList = studyMemberRepository.findByJoinMemberId(memberId).stream()
                .map(StudyMember::getStudy)
                .filter(s -> s.getStatus().equals(StudyStatus.OPEN))
                .collect(Collectors.toList());

        openStudyList.stream()
                .filter(participating -> participating.getStartTime().isBefore(study.getEndTime())
                        && participating.getEndTime().isAfter(study.getStartTime()))
                .findAny().ifPresent(s -> {
                    throw new IllegalStateException("이미 진행중인 스터디가 있습니다.");
                });
    }
}
