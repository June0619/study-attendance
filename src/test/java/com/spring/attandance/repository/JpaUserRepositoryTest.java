package com.spring.attandance.repository;

import com.spring.attandance.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JpaUserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("[통합] 회원 저장")
    void save() {
        // given
        User givenUser = User.builder()
                .name("TEST_MAN")
                .mobile("01012345678")
                .build();

        userRepository.save(givenUser);

        // when
        User findUser = userRepository.findOne(givenUser.getId()).get();

        // then
        // 1.Expect givenUser == findUser
        Assertions.assertThat(givenUser).isSameAs(findUser);
    }
}