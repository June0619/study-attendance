package com.spring.attandance.service;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.group.GroupUpdateDTO;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.group.GroupCreateDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.MemberRepository;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.service.validator.GroupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spring.attandance.domain.enums.GroupRole.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repository;
    private final GroupValidator validator;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public Long create(GroupCreateDTO groupCreateDTO, LoginMemberDTO loginMember) {

        //생성 시 로그인 유저가 스터디 그룹의 소유자가 된다.
        Member master = memberRepository.findById(loginMember.getId())
                .orElseThrow(IllegalStateException::new);

        Group group = Group.builder()
                .name(groupCreateDTO.getName())
                .master(master)
                .build();

        //1. 스터디 그룹 생성 제한 초과 여부 Validation
        validator.exceedLimitGroupCount(loginMember.getId());

        //2. 스터디 그룹 생성
        repository.save(group);

        //3. 스터디 그룹 생성자 등록
        GroupMember groupMember = GroupMember.builder()
                .member(master)
                .group(group)
                .role(MASTER)
                .build();

        groupMemberRepository.save(groupMember);

        return group.getId();
    }

    @Transactional
    public Long update(Long groupId, GroupUpdateDTO groupUpdateDTO, LoginMemberDTO loginMemberDTO) {

        //1. 스터디 그룹 존재 여부 Validation
        Group group = repository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 스터디 그룹입니다."));

        //2. 스터디 그룹 수정 권한 Validation
        validator.isGroupMaster(loginMemberDTO.getId(), groupId);

        //3. 스터디 그룹 수정
        group.update(groupUpdateDTO.getName());

        return group.getId();
    }

    public void enroll(Long groupId, LoginMemberDTO loginMemberDTO) {

        //1. 스터디 그룹 존재 여부 Validation
        Group group = repository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 스터디 그룹입니다."));

        //2. 스터디 그룹 가입 여부 Validation
        validator.isGroupMember(loginMemberDTO.getId(), groupId);

        //3. 신청자 존재 여부 Validation
        Member loginMember = memberRepository.findById(loginMemberDTO.getId())
                .orElseThrow(IllegalStateException::new);

        //4. 스터디 그룹 가입자 등록 (가입 대기 상태로 등록)
        GroupMember groupMember = GroupMember.builder()
                .member(loginMember)
                .group(group)
                .role(WAIT)
                .build();

        groupMemberRepository.save(groupMember);
    }

    public void resign() {

    }

    public void delete() {

    }

}

