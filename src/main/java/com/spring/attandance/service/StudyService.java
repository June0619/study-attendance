package com.spring.attandance.service;

import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.study.StudyCreateDTO;
import com.spring.attandance.controller.dto.study.StudyUpdateDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.repository.MemberRepository;
import com.spring.attandance.repository.StudyMemberRepository;
import com.spring.attandance.repository.StudyRepository;
import com.spring.attandance.service.validator.StudyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final StudyValidator studyValidator;

    @Transactional
    public Long create(StudyCreateDTO dto, LoginMemberDTO loginMember) {

        //1. 그룹 및 로그인 회원 조회
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다.")
        );

        Member owner = memberRepository.findById(loginMember.getId())
                .orElseThrow(IllegalStateException::new);

        //2. 스터디 Entity 객체 생성
        Study study = Study.createStudy(owner, group, dto.getStartTime(), dto.getEndTime());

        //1. 스터디 생성 권한 체크
        studyValidator.isStudyGroupMember(loginMember.getId(), group.getId());

        //2. 중복 시간대 스터디 체크
        studyValidator.periodCheck(loginMember.getId(), study);

        //3. 스터디 생성
        studyRepository.save(study);

        return study.getId();
    }

    @Transactional
    public Long update(StudyUpdateDTO dto, LoginMemberDTO loginMember) {

        //1. DTO 변환 및 스터디 조회
        Study study = studyRepository.findById(dto.getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 스터디 입니다.")
        );
        Group group = study.getGroup();

        //2. 스터디 수정 권한 체크
        studyValidator.hasStudyUpdatePermission(loginMember.getId(), study.getId(), group.getId());

        //3. 스터디 수정
        study.updateStudyInfo(dto.getStartTime(), dto.getEndTime());

        return study.getId();
    }

}
