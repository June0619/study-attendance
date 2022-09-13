package com.spring.attandance.config;

import com.spring.attandance.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AuthorizationParserImplTest {

    @Autowired EntityManager em;
    @Autowired AuthorizationParserImpl authorizationParser;

    @BeforeEach
    void setUp() {
        Member m = Member.builder()
                .mobile("01012345678")
                .name("TestMember")
                .build();

        em.persist(m);
    }

    @Test
    @DisplayName("[통합] parse 성공")
    void parse() {

        String authorization = "01012345678";
        assertDoesNotThrow(() -> authorizationParser.parse(authorization));
    }

    @Test
    @DisplayName("[통합] parse 실패")
    void parse_fail() {

        String authorization = "01012345679";
        assertThrows(RuntimeException.class, () -> authorizationParser.parse(authorization));
    }

}