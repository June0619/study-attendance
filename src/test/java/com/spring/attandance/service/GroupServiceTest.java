package com.spring.attandance.service;

import com.spring.attandance.controller.dto.group.GroupCreateDTO;
import com.spring.attandance.controller.dto.group.GroupUpdateDTO;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceTest {

    @Autowired
    GroupService groupService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("[통합] 스터디 그룹 생성")
    void create() {
        //given
        Member member = Member.builder()
                .name("test")
                .email("test@test.co.kr")
                .mobile("01012345678")
                .build();

        memberRepository.save(member);

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(member);
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO();
        groupCreateDTO.setName("test_group");

        groupService.create(groupCreateDTO, loginMemberDTO);

        em.flush();
        em.clear();

        //when
        List<Group> groupResult = groupRepository.findAll();
        List<GroupMember> groupMemberResult = groupMemberRepository.findAll();

        Member findMember = em.find(Member.class, member.getId());

        //then
        //생성된 그룹 갯수 확인
        assertEquals(1, groupResult.size());
        //생성된 그룹 멤버 갯수 확인
        assertEquals(1, groupMemberResult.size());
        //oneToMany 관계 확인
        assertThat(findMember.getGroupMembers()).containsExactly(groupMemberResult.get(0));
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 수정")
    void update() {
        //given
        Member member = Member.builder()
                .name("test")
                .build();

        memberRepository.save(member);

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(member);
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO();
        groupCreateDTO.setName("test_group");

        Long savedGroupId = groupService.create(groupCreateDTO, loginMemberDTO);

        em.flush();
        em.clear();

        GroupUpdateDTO groupUpdateDTO = new GroupUpdateDTO();
        groupUpdateDTO.setName("update_group");

        //when
        Long updatedGroupId = groupService.update(savedGroupId, groupUpdateDTO, loginMemberDTO);
        Group updatedGroup = em.find(Group.class, updatedGroupId);

        //then
        assertThat(updatedGroup.getName()).isEqualTo("update_group");
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 등록_성공")
    void enroll() {
        //given
        Member member = Member.builder()
                .name("test")
                .build();

        em.persist(member);

        Group group = Group.builder()
                .name("test_group")
                .build();

        em.persist(group);

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(member);

        //when
        groupService.enroll(group.getId(), loginMemberDTO);
        GroupMember result = groupMemberRepository.findByMemberIdAndGroupId(member.getId(), group.getId()).get();

        //then
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getGroup()).isEqualTo(group);
        assertThat(result.getRole()).isEqualTo(GroupRole.WAIT);
    }

}