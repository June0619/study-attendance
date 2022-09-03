package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JpaUserRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("[통합] 회원 저장")
    void save() {
        // given
        Member givenMember = Member.builder()
                .name("TEST_MAN")
                .mobile("01012345678")
                .build();

        memberRepository.save(givenMember);

        // when
        Member findMember = memberRepository.findById(givenMember.getId()).get();

        // then
        // 1.Expect givenUser == findUser
        assertThat(findMember).isSameAs(givenMember);
        assertThat(findMember.getCreatedAt().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }
}