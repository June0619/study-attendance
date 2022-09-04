package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberQueryRepositoryTest {

    @Autowired MemberQueryRepository repository;
    @Autowired EntityManager em;

    @DisplayName("[통합] QueryRepository findOne 메서드 테스트")
    @Test
    void findOne() {
        //given
        Member member = new Member("test_user", "01012345678", "test@email.co.kr");
        em.persist(member);

        //when
        MemberSearchCondition condition = new MemberSearchCondition(null, "01012345678", null);
        Optional<Member> findByMobile = repository.findOne(condition);

        //then
        assertEquals(findByMobile.get(), member);
    }



}