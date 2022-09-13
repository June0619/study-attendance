package com.spring.attandance.service;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.studyGroup.StudyGroupCreateDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.StudyGroup;
import com.spring.attandance.repository.MemberRepository;
import com.spring.attandance.repository.StudyGroupRepository;
import com.spring.attandance.repository.query.StudyGroupQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository repository;
    private final StudyGroupQueryRepository queryRepository;
    private final StudyGroupValidator validator;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(StudyGroupCreateDTO dto) {

        LoginMemberDTO loginMember = MemberThreadLocal.get();
        Member loginMemberEntity = memberRepository.findById(loginMember.getId())
                .orElseThrow(IllegalStateException::new);

        //1. 스터디 그룹 생성 제한 초과 여부 Validation
        validator.exceedLimitGroupCount(loginMember.getId());
        dto.setMaster(loginMemberEntity);

        //2. 스터디 그룹 생성
        StudyGroup studyGroup = dto.toEntity();
        StudyGroup savedGroup = repository.save(studyGroup);

        return savedGroup.getId();
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

