package com.spring.attandance.config;

import com.spring.attandance.config.auth.AuthorizationParserImpl;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.enums.GroupRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AuthorizationParserImplTest {

    @Autowired EntityManager em;
    @Autowired
    AuthorizationParserImpl authorizationParser;

    final String authorization = "01012345678";

    @BeforeEach
    void setUp() {
        Member m = Member.builder()
                .mobile(authorization)
                .name("TestMember")
                .build();

        Group g = Group.builder()
                .name("TestGroup")
                .master(m)
                .build();

        GroupMember gm = GroupMember.builder()
                .member(m)
                .group(g)
                .role(GroupRole.MASTER)
                .build();

        em.persist(m);
        em.persist(g);
        em.persist(gm);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("[통합] parse 성공")
    void parse() {
        LoginMemberDTO dto = authorizationParser.parse(authorization);
        assertFalse(dto.isGuest());
        assertThat(dto.getMobile()).isEqualTo("01012345678");
        assertThat(dto.getGroups().size()).isEqualTo(1);
        assertThat(dto.getGroups().get(0).getRole()).isEqualTo(GroupRole.MASTER);
    }

    @Test
    @DisplayName("[통합] parse 실패")
    void parse_fail() {
        assertTrue(authorizationParser.parse(null).isGuest());
    }

}