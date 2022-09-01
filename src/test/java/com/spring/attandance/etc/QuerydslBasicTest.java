package com.spring.attandance.etc;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.spring.attandance.domain.QMember.member;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
        Member memberA = new Member("memberA", null);
        em.persist(memberA);
    }

    @Test
    void querydslTest() {
        //when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.name.eq("memberA"))
                .fetch();

        //then
        assertThat(result).extracting("name").containsExactly("memberA");
    }

}
