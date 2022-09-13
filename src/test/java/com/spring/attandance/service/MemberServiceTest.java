package com.spring.attandance.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.controller.dto.member.MemberUpdateDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static com.spring.attandance.domain.QMember.member;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired JPAQueryFactory queryFactory;
    @Autowired EntityManager em;

    @Test
    @DisplayName("[통합] 회원 가입 - 성공")
    void join() {

        //given
        Member givenMember = Member.builder()
                .name("테스트")
                .mobile("01012345678")
                .email("test@test.co.kr")
                .build();

        //when
        Long memberId = memberService.join(givenMember);

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        //then
        assertThat(findMember).isEqualTo(givenMember);
        assertThat(findMember.getName()).isEqualTo(givenMember.getName());
        assertThat(findMember.getMobile()).isEqualTo(givenMember.getMobile());
        assertThat(findMember.getEmail()).isEqualTo(givenMember.getEmail());
    }

    @Test
    @DisplayName("[통합] 회원 가입 - 실패")
    void join_fail() {
        //given
        Member givenMember = Member.builder()
                .name("테스트")
                .mobile("01012345678")
                .email("test@test.co.kr")
                .build();

        //when
        memberService.join(givenMember);

        //then
        assertThrows(IllegalStateException.class,
                () -> memberService.join(givenMember));
    }

    @Test
    @DisplayName("[통합] 회원 업데이트")
    void memberUpdate() {

        //given
        Member givenMember = Member.builder()
                .name("테스트")
                .mobile("01012345678")
                .email("test@test.co.kr")
                .build();

        Long givenId = memberService.join(givenMember);

        MemberUpdateDTO dto = new MemberUpdateDTO();
        dto.setId(givenId);
        dto.setName("테스트2");
        dto.setEmail("test2@test.co.kr");

        memberService.updateMember(dto);

        //when
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.id.eq(givenId))
                .fetchOne();

        //then
        assertThat(findMember.getName()).isEqualTo(dto.getName());
        assertThat(findMember.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    @DisplayName("[통합] 회원 삭제 - 성공")
    void memberDelete() {

        //given
        Member givenMember = Member.builder()
                .name("테스트")
                .mobile("01012345678")
                .email("test@email.co.kr")
                .build();

        //when
        Long givenId = memberService.join(givenMember);

        //then
        assertDoesNotThrow(() -> memberService.deleteMember(givenId));

        Member findMember = queryFactory.selectFrom(member)
                .where(member.id.eq(givenId))
                .fetchOne();

        assertThat(findMember).isNull();
    }

    @Test
    @DisplayName("[통합] 회원 삭제 - 실패")
    void memberDelete_fail() {

        //given
        Member memberA = Member.builder()
                .name("memberA")
                .mobile("01012345678")
                .build();

        Group group = Group.builder()
                .name("스터디그룹")
                .master(memberA)
                .build();

        Member memberB = Member.builder()
                .name("memberB")
                .mobile("01012345679")
                .build();

        Study study = new Study(memberB, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));

        //when
        Long memberAId = memberService.join(memberA);
        Long memberBId = memberService.join(memberB);

        em.persist(group);
        em.persist(study);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.deleteMember(memberAId));
        assertThrows(IllegalStateException.class, () -> memberService.deleteMember(memberBId));
    }
}