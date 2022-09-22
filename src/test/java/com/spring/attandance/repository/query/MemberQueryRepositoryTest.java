package com.spring.attandance.repository.query;

import com.spring.attandance.controller.dto.member.MemberResponseDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.repository.query.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberQueryRepositoryTest {

    @Autowired
    MemberQueryRepository repository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("[통합] QueryRepository.findOne")
    void findOne() {
        //given
        Member member = Member.builder()
                .name("test_user")
                .mobile("01012345678")
                .build();

        em.persist(member);

        //when
        MemberSearchCondition condition = new MemberSearchCondition(null, "01012345678", null);
        Optional<Member> findByMobile = repository.searchMember(condition);

        //then
        assertEquals(findByMobile.get(), member);
    }

    @Test
    @DisplayName("[통합] QueryRepository.searchMemberList")
    void searchMemberList() {
        //given
        Member memberA = Member.builder()
                .name("memberA")
                .mobile("01012345678")
                .build();

        Member memberB = Member.builder()
                .name("memberB")
                .mobile("01012345679")
                .build();

        Member memberC = Member.builder()
                .name("memberC")
                .mobile("01056781234")
                .build();

        Member memberD = Member.builder()
                .name("memberD")
                .mobile("01056781235")
                .build();

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);

        Group group = Group.builder()
                .name("groupA")
                .build();

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(memberA)
                .build();

        em.persist(group);
        em.persist(groupMember);

        em.flush();
        em.clear();

        //when
        MemberSearchCondition condition = new MemberSearchCondition(null, null, null);
        Page<MemberResponseDTO> result1 = repository.searchMemberList(condition, PageRequest.of(0, 2));
        condition.setName("memberB");
        Page<MemberResponseDTO> result2 = repository.searchMemberList(condition, PageRequest.of(0, 2));

        //then
        assertThat(result1.getTotalPages()).isEqualTo(2);
        assertThat(result2.getContent()).containsExactly(new MemberResponseDTO(memberB));
    }

}