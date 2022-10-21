package com.spring.attandance.service;

import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.study.StudyCreateDTO;
import com.spring.attandance.controller.dto.study.StudyUpdateDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.domain.enums.StudyStatus;
import com.spring.attandance.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class StudyServiceTest {

    @Autowired StudyService studyService;
    @Autowired StudyRepository studyRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("[통합] 스터디 생성")
    void create() {
        //given
        Member member = Member.builder()
                .name("Member")
                .mobile("01012345679")
                .build();

        Group group = Group.builder()
                .name("test_group")
                .build();

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(member)
                .role(GroupRole.MEMBER)
                .build();

        em.persist(member);
        em.persist(group);
        em.persist(groupMember);

        LoginMemberDTO loginUser = new LoginMemberDTO(member);

        //Create DTO 생성
        StudyCreateDTO studyCreateDTO = new StudyCreateDTO();
        studyCreateDTO.setGroupId(group.getId());
        studyCreateDTO.setStartTime(LocalDateTime.now());
        studyCreateDTO.setEndTime(LocalDateTime.now().plusHours(1));

        //when
        Long savedStudyId = studyService.create(studyCreateDTO, loginUser);
        Study findStudy = em.find(Study.class, savedStudyId);

        //then
        assertThat(findStudy.getOwner()).isEqualTo(member);
        assertThat(findStudy.getGroup()).isEqualTo(group);
        assertThat(findStudy.getStartTime()).isEqualTo(studyCreateDTO.getStartTime());
        assertThat(findStudy.getEndTime()).isEqualTo(studyCreateDTO.getEndTime());
        assertThat(findStudy.getStatus()).isEqualTo(StudyStatus.OPEN);
    }

    @Test
    @DisplayName("[통합] 스터디 수정 - 성공")
    void update() {
        //given
        Member member = Member.builder()
                .name("Member")
                .mobile("01012345679")
                .build();

        Group group = Group.builder()
                .name("test_group")
                .build();

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(member)
                .role(GroupRole.MEMBER)
                .build();

        Study study = Study.builder()
                .group(group)
                .owner(member)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        em.persist(member);
        em.persist(group);
        em.persist(groupMember);
        em.persist(study);

        LoginMemberDTO loginUser = new LoginMemberDTO(member);

        //Update DTO 생성
        StudyUpdateDTO studyUpdateDTO = new StudyUpdateDTO();
        studyUpdateDTO.setId(study.getId());
        studyUpdateDTO.setStartTime(LocalDateTime.now().plusHours(2));
        studyUpdateDTO.setEndTime(LocalDateTime.now().plusHours(3));

        //when
        studyService.update(studyUpdateDTO, loginUser);
        Study findStudy = em.find(Study.class, studyUpdateDTO.getId());

        //then
        assertThat(findStudy.getStartTime()).isEqualTo(studyUpdateDTO.getStartTime());
        assertThat(findStudy.getEndTime()).isEqualTo(studyUpdateDTO.getEndTime());
        assertThat(findStudy.getStatus()).isEqualTo(StudyStatus.OPEN);
    }

    @Test
    @DisplayName("[통합] 스터디 수정 - 실패 - 스터디 생성자가 아닌 경우")
    void update_fail1() {
        //given
        Member member = Member.builder()
                .name("Member")
                .mobile("01012345679")
                .build();

        Member member2 = Member.builder()
                .name("Member2")
                .mobile("01012345678")
                .build();

        Group group = Group.builder()
                .name("test_group")
                .build();

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(member)
                .role(GroupRole.MEMBER)
                .build();

        GroupMember groupMember2 = GroupMember.builder()
                .group(group)
                .member(member2)
                .role(GroupRole.MEMBER)
                .build();

        Study study = Study.builder()
                .group(group)
                .owner(member)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        em.persist(member);
        em.persist(member2);
        em.persist(group);
        em.persist(groupMember);
        em.persist(groupMember2);
        em.persist(study);

        LoginMemberDTO loginUser = new LoginMemberDTO(member2);

        //Update DTO 생성
        StudyUpdateDTO studyUpdateDTO = new StudyUpdateDTO();
        studyUpdateDTO.setId(study.getId());
        studyUpdateDTO.setStartTime(LocalDateTime.now().plusHours(2));
        studyUpdateDTO.setEndTime(LocalDateTime.now().plusHours(3));

        //when
        assertThrows(IllegalStateException.class, () -> studyService.update(studyUpdateDTO, loginUser));
    }

}