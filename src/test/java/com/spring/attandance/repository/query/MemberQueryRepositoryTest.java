package com.spring.attandance.repository.query;

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
        Member member = new Member("test_user", "01012345678", "test@email.co.kr");
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
        Member memberA = new Member("memberA", "01012345678", "test@email.co.kr");
        Member memberB = new Member("memberB", "01012345679", "test@email.co.kr");
        Member memberC = new Member("memberC", "01056781234", "test@email.co.kr");
        Member memberD = new Member("memberD", "01056781235", "test@email.co.kr");

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);

        //when
        MemberSearchCondition condition = new MemberSearchCondition(null, null, null);
        Page<Member> result1 = repository.searchMemberList(condition, PageRequest.of(0, 2));
        condition.setName("memberB");
        Page<Member> result2 = repository.searchMemberList(condition, PageRequest.of(0, 2));

        //then
        assertThat(result1.getTotalPages()).isEqualTo(2);
        assertThat(result2.getContent()).containsExactly(memberB);
    }

}