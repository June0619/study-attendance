package com.spring.attandance.service;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.studyGroup.StudyGroupCreateDTO;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.StudyGroup;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.MemberRepository;
import com.spring.attandance.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.attandance.domain.enums.GroupRole.MASTER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository repository;
    private final StudyGroupValidator validator;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public Long create(StudyGroupCreateDTO dto) {

        LoginMemberDTO loginMember = MemberThreadLocal.get();

        Member loginMemberEntity = memberRepository.findById(loginMember.getId())
                .orElseThrow(IllegalStateException::new);

        StudyGroup studyGroup = StudyGroup.builder()
                .name(dto.getName())
                .master(loginMemberEntity)
                .build();

        //1. 스터디 그룹 생성 제한 초과 여부 Validation
        validator.exceedLimitGroupCount(loginMember.getId());

        //2. 스터디 그룹 생성
        repository.save(studyGroup);

        //3. 스터디 그룹 생성자 등록
        GroupMember groupMember = GroupMember.builder()
                .member(loginMemberEntity)
                .studyGroup(studyGroup)
                .role(MASTER)
                .build();

        groupMemberRepository.save(groupMember);

        return studyGroup.getId();
    }

    public void update() {

    }

    public void enroll() {

    }

    public void resign() {

    }

    public void delete() {

    }

}

